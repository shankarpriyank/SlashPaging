package com.priyank.paging.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.priyank.paging.data.local.dao.NewsItemDao
import com.priyank.paging.data.local.dao.NewsRemoteKeysDao
import com.priyank.paging.domain.model.NewsItem
import com.priyank.paging.domain.model.NewsRemoteKeys

@Database(entities = [NewsItem::class,NewsRemoteKeys::class], version = 6)
abstract class NewsDatabase :RoomDatabase(){
    abstract fun NewsItemDao(): NewsItemDao
    abstract fun NewsRemoteKeysDao(): NewsRemoteKeysDao
}