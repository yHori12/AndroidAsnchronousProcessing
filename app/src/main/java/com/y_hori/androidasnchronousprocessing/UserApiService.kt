package com.y_hori.androidasnchronousprocessing

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object UserApiService : BaseService() {
    val userApi: UserApiInterface = getRetrofit().create(UserApiInterface::class.java)
}

open class BaseService {
    companion object {
        private const val baseUrl = "https://qiita.com"
    }

    private val client: OkHttpClient
        get() {
            return OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
                .build()
        }

    fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .client(client)
            .baseUrl(baseUrl)
            .addConverterFactory(
                MoshiConverterFactory.create(
                    Moshi.Builder().add(
                        KotlinJsonAdapterFactory()
                    ).build()
                )
            )
            .build()
    }

}
