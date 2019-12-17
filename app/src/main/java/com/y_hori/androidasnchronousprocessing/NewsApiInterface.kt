package com.y_hori.androidasnchronousprocessing

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface  NewsApiInterface{
    //fetches latest news with the required query params
    @GET("v2/everything")
    suspend fun fetchLatestNewsAsync(@Query("q") query: String,
                             @Query("sortBy") sortBy : String) : Response<LatestNews>
}
