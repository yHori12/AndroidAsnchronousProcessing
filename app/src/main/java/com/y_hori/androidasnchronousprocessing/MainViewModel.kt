package com.y_hori.androidasnchronousprocessing

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel : ViewModel() {

    private val repository = UserRepository(UserApiService.userApi)

    private val _userList = MutableLiveData<MutableList<User>>()
    val userList: LiveData<MutableList<User>>
        get() = _userList

    init {
        fetchUser()
    }

    private fun fetchUser() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val user = repository.fetchUserList(1, 2)
                user?.let {
                    _userList.postValue(user)
                }
            }
        }
    }
}
