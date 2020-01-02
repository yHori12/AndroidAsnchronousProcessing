package com.y_hori.androidasnchronousprocessing

import android.util.Log
import retrofit2.Response
import java.io.IOException

sealed class NetworkResult<out T : Any> {
    data class Success<out T : Any>(val output: T) : NetworkResult<T>()
    data class Error(val exception: Exception) : NetworkResult<Nothing>()
}

open class BaseRepository {
    suspend fun <T : Any> apiOutput(
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


class UserRepository(private val apiInterface: UserApiInterface) : BaseRepository() {
    suspend fun fetchUserList(page: Int, perPage: Int): MutableList<User>? {
        val result = apiOutput(
            call = { apiInterface.fetchUser(page, perPage) },
            error = "calling fetchUserList failed"
        )
        var output: MutableList<User>? = null

        when (result) {
            is NetworkResult.Success ->
                output = result.output
            is NetworkResult.Error ->
                Log.d("Error", "${result.exception}")
        }
        return output
    }
}


