package com.priyank.paging.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.priyank.paging.domain.model.NewsItem

@Dao
interface NewsItemDao {
    @Query("SELECT * FROM NewsTable")
    fun getAllNews(): PagingSource<Int, NewsItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNews(news: List<NewsItem>)

    @Query("DELETE FROM NewsTable")
    suspend fun deleteAllNews()
}