package com.example.githubapp2.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubapp2.data.api.ApiConfig
import com.example.githubapp2.data.response.GithubUserResponse
import com.example.githubapp2.util.Event
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel : ViewModel() {
    private val _user = MutableLiveData<GithubUserResponse>()
    val user : LiveData<GithubUserResponse> = _user

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    private val _toastText = MutableLiveData<Event<String>>()
    val toastText : LiveData<Event<String>> = _toastText

    companion object{
        private const val ERROR_MSG = "Unknown error occured"
    }

    fun showUser(username : String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUser(username)
        client.enqueue(object : Callback<GithubUserResponse> {
            override fun onResponse(
                call: Call<GithubUserResponse>,
                response: Response<GithubUserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _user.value = response.body()
                } else {
                    _toastText.value = Event(ERROR_MSG)
                }
            }

            override fun onFailure(call: Call<GithubUserResponse>, t: Throwable) {
                _isLoading.value = false
                _toastText.value = Event(ERROR_MSG)
            }
        })
    }
}