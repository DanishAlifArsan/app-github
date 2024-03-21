package com.example.githubapp2.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubapp2.data.api.ApiConfig
import com.example.githubapp2.data.response.GithubResponse
import com.example.githubapp2.data.response.ItemsItem
import com.example.githubapp2.util.Event
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListViewModel : ViewModel() {
    private val _user = MutableLiveData<List<ItemsItem?>?>()
    val user : LiveData<List<ItemsItem?>?> = _user

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    private val _toastText = MutableLiveData<Event<String>>()
    val toastText : LiveData<Event<String>> = _toastText

    companion object{
        private const val ERROR_MSG = "Unknown error occured"
        private const val USERNAME = "1"
    }

    init {
        showUsers(USERNAME)
    }

    fun showUsers(username : String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getAllUsers(username)
        client.enqueue(object : Callback<GithubResponse> {
            override fun onResponse(call: Call<GithubResponse>, response: Response<GithubResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _user.value = response.body()?.items
                } else {
                    _toastText.value = Event(ERROR_MSG)
                }
            }

            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                _isLoading.value = false
                _toastText.value = Event(ERROR_MSG)
            }
        })
    }
}