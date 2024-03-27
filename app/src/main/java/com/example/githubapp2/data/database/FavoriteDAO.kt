package com.example.githubapp2.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface FavoriteDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(favorite: Favorite)
    @Delete
    fun delete(favorite: Favorite)
    @Query("SELECT * FROM Favorite WHERE username = :username")
    fun getFavoriteUserByUsername(username: String): LiveData<Favorite>
    @Query("SELECT * FROM favorite")
    fun getAllFavoriteUser() : LiveData<List<Favorite>>
}