package com.priyank.paging.domain

import androidx.lifecycle.ViewModel
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import com.priyank.paging.data.repository.Repository
import com.priyank.paging.domain.model.NewsItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @OptIn(ExperimentalPagingApi::class)
@Inject constructor(
   private val repository: Repository
) :ViewModel(){
    @OptIn(ExperimentalPagingApi::class)



    fun  getAllNews(): Flow<PagingData<NewsItem>> {

        return repository.getAllImages()
    }
}