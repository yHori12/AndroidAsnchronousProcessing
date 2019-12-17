package com.y_hori.androidasnchronousprocessing

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel : ViewModel() {

    private val repository = NewsRepo(NewsApiService.newsApi)

    val liveData = MutableLiveData<MutableList<Article>>()

    fun getApi() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val user = repository.getLatestNews()
                liveData.postValue(user)
            }
        }
    }
}
