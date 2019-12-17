package com.y_hori.androidasnchronousprocessing

import android.util.Log
import retrofit2.Response
import java.io.IOException

class NewsRepo(private val apiInterface: NewsApiInterface) : BaseRepository() {
    suspend fun getLatestNews(): MutableList<Article>? {
        val result = newsApiOutput(
            call = { apiInterface.fetchLatestNewsAsync("Nigeria", "publishedAt") },
            error = "Error fetching news")
        var output: MutableList<Article>? = null
        when (result) {
            is NetworkResult.Success ->
                output = result.output.articles.toMutableList()
            is NetworkResult.Error ->
                Log.e("Error", "${result.exception}")
        }
        return output
    }
}


open class BaseRepository {
    suspend fun <T : Any> safeApiCall(call: suspend () -> Response<T>, error: String): T? {
        val result = newsApiOutput(call, error)
        var output: T? = null
        when (result) {
            is NetworkResult.Success ->
                output = result.output
            is NetworkResult.Error -> Log.e("Error", "${result.exception}")
        }
        return output

    }

    suspend fun <T : Any> newsApiOutput(
        call: suspend () -> Response<T>,
        error: String
    ): NetworkResult<T> {
        val response = call.invoke()
        return if (response.isSuccessful)
            NetworkResult.Success(response.body()!!)
        else
            NetworkResult.Error(IOException(error))
    }
}


sealed class NetworkResult<out T : Any> {
    data class Success<out T : Any>(val output: T) : NetworkResult<T>()
    data class Error(val exception: Exception) : NetworkResult<Nothing>()
}


