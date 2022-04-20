package com.priyank.paging.data.paging

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.priyank.paging.data.local.NewsDatabase
import com.priyank.paging.data.remote.NewsApi
import com.priyank.paging.domain.model.NewsItem
import com.priyank.paging.domain.model.NewsRemoteKeys
import com.priyank.paging.presentation.NewsItem
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class RemoteMediator @Inject constructor(
    private val newsApi : NewsApi,
    private val newsDatabase: NewsDatabase
): RemoteMediator<Int, NewsItem>() {


    private val newsItemDao = newsDatabase.NewsItemDao()
    private val newsRemoteKeysDao = newsDatabase.NewsRemoteKeysDao()
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, NewsItem>
    ): MediatorResult {
        return try {
            val currentPage = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextPage?.minus(1) ?: 1
                }
                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevPage = remoteKeys?.prevPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    prevPage
                }
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKeys?.nextPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    nextPage
                }
            }

            val response = newsApi.getAllNews(page = currentPage, perPage = 20)
            val endOfPaginationReached = response.isEmpty()

            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (endOfPaginationReached) null else currentPage + 1

             newsDatabase.withTransaction {

                 if (loadType == LoadType.REFRESH) {
                     newsItemDao.deleteAllNews()
                     newsRemoteKeysDao.deleteAllRemoteKeys()
                 }
                 val keys =response.map {
                     NewsRemoteKeys(
                         id = it.id,
                         prevPage = prevPage,
                         nextPage = nextPage
                     )
                 }


                 newsRemoteKeysDao.addAllRemoteKeys(remoteKeys = keys)
                 newsItemDao.addNews(news = response)
             }

            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception) {
            Log.e("KEY", e.message!!)
            return MediatorResult.Error(e)

        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, NewsItem>
    ): NewsRemoteKeys? {
        return state.anchorPosition?.let {
            state.closestItemToPosition(it)?.id?.let { id ->
                newsRemoteKeysDao.getRemoteKeys(id = id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, NewsItem>
    ): NewsRemoteKeys?  {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let {
                newsRemoteKeysDao.getRemoteKeys(id = it.id)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, NewsItem>
    ):  NewsRemoteKeys?  {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let {
                newsRemoteKeysDao.getRemoteKeys(id = it.id)
            }
    }
    }
