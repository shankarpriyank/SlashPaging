package com.priyank.paging.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.priyank.paging.domain.model.NewsRemoteKeys
@Dao
interface NewsRemoteKeysDao {
    @Query("SELECT * FROM RemoteKeyTable WHERE id =:id")
    suspend fun getRemoteKeys(id: String): NewsRemoteKeys

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllRemoteKeys(remoteKeys: List<NewsRemoteKeys>)

    @Query("DELETE FROM  RemoteKeyTable")
    suspend fun deleteAllRemoteKeys()
}