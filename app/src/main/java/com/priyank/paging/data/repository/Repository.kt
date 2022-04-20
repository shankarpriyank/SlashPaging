package com.priyank.paging.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.priyank.paging.data.local.NewsDatabase
import com.priyank.paging.data.paging.RemoteMediator
import com.priyank.paging.data.remote.NewsApi
import com.priyank.paging.domain.model.NewsItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ExperimentalPagingApi
class Repository @Inject constructor(
    private val newsApi: NewsApi,
    private val newsDatabase: NewsDatabase
) {

    fun getAllImages(): Flow<PagingData<NewsItem>> {
        val pagingSourceFactory = { newsDatabase.NewsItemDao().getAllNews()}
        return Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator = RemoteMediator(
                newsApi = newsApi,
                newsDatabase = newsDatabase
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }



}