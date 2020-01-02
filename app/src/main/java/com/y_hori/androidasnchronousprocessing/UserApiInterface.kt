package com.y_hori.androidasnchronousprocessing

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UserApiInterface {
    @GET("/api/v2/users")
    suspend fun fetchUser(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): Response<MutableList<User>>
}
