package com.example.githubapp2.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.githubapp2.data.database.Favorite
import com.example.githubapp2.data.database.FavoriteDAO
import com.example.githubapp2.data.database.FavoriteDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteRepository(application: Application) {
    private lateinit var favoriteDAO : FavoriteDAO
    private val executorService : ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteDatabase.getDatabase(application)
        favoriteDAO = db.favoriteDAO()
    }

    fun getFavoriteUserByUsername(username : String) : LiveData<Favorite> = favoriteDAO.getFavoriteUserByUsername(username)
    fun getAllFavoriteUser() : LiveData<List<Favorite>> = favoriteDAO.getAllFavoriteUser()
    fun insertNote(favorite : Favorite) {
        executorService.execute{favoriteDAO.insert(favorite)}
    }
    fun deleteNote(favorite : Favorite) {
        executorService.execute{favoriteDAO.delete(favorite)}
    }
}