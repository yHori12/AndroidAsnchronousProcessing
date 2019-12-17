package com.y_hori.androidasnchronousprocessing

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

// API基底
open class BaseApi<T>(apiClass: Class<T>) {
    protected val api: T
    companion object {
        private const val baseUrl = "https://qiita.com/api/v2/items?page=1&per_page=20"
        private val client: OkHttpClient
            get() {
                return OkHttpClient.Builder()
                    .addInterceptor(HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    })
                    .build()
            }
    }

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(
                MoshiConverterFactory.create(
                    Moshi.Builder().add(
                        KotlinJsonAdapterFactory()
                    ).build()
                )
            )
            .client(client)
            .build()

        api = retrofit.create(apiClass)
    }
}
