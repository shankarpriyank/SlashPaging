package com.priyank.paging.domain.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.priyank.paging.Const.Companion.NEWS_TABLE
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = NEWS_TABLE)
data class NewsItem(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    @Embedded
    val urls: Urls,
    val likes: Int,
    @Embedded
    val user: User
)
