package com.priyank.paging.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.priyank.paging.Const.Companion.REMOTE_KEYS_TABLE

@Entity(tableName = REMOTE_KEYS_TABLE )
data class NewsRemoteKeys(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val prevPage: Int?,
    val nextPage: Int?
)