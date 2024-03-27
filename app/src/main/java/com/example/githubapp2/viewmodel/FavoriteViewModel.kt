package com.example.githubapp2.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.githubapp2.data.database.Favorite
import com.example.githubapp2.repository.FavoriteRepository

class FavoriteViewModel(application: Application) : ViewModel() {
    private val repository : FavoriteRepository = FavoriteRepository(application)

    fun getAllFavoriteUser() : LiveData<List<Favorite>> = repository.getAllFavoriteUser()
    fun getFavoriteUserByUsername(username : String) : LiveData<Favorite> = repository.getFavoriteUserByUsername(username)
    fun insert(favorite: Favorite) = repository.insertNote(favorite)
    fun delete(favorite: Favorite) = repository.deleteNote(favorite)
}