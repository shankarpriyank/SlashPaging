package com.priyank.paging.data.remote


import com.priyank.paging.Const.Companion.API_KEY
import com.priyank.paging.domain.model.NewsItem
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query


interface NewsApi {


    @Headers("Authorization: Client-ID TQnIF0yTlEHqdRKgM-DOIkRsfuI0bVrXFCYIJO-Tfxg")
    @GET("/photos")
    suspend fun getAllNews(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): List<NewsItem>
}