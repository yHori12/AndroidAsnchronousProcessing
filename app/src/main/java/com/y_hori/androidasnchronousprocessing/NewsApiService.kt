package com.y_hori.androidasnchronousprocessing

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object NewsApiService {
    //creating a Network Interceptor to add api_key in all the request as authInterceptor
//    private val interceptor = Interceptor { chain ->
//        val url = chain.request().url.newBuilder().addQueryParameter("apiKey", API_KEY).build()
//        val request = chain.request()
//            .newBuilder()
//            .url(url)
//            .build()
//        chain.proceed(request)
//    }
//    // we are creating a networking client using OkHttp and add our authInterceptor.
//    private val apiClient = OkHttpClient().newBuilder().addInterceptor(interceptor).build()


    private val client: OkHttpClient
        get() {
            return OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
                .build()
        }


    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .client(client)
            .baseUrl("https://newsapi.org/")
            .addConverterFactory(
                MoshiConverterFactory.create(
                    Moshi.Builder().add(
                        KotlinJsonAdapterFactory()
                    ).build()
                )
            )
            .build()
    }

    val newsApi: NewsApiInterface = getRetrofit().create(NewsApiInterface::class.java)
}
