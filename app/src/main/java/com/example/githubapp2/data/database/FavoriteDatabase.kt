package com.example.githubapp2.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Favorite::class], version = 1)
abstract class FavoriteDatabase : RoomDatabase() {
    abstract fun favoriteDAO() : FavoriteDAO

    companion object{
        @Volatile
        private var INSTANCE : FavoriteDatabase? = null

        @JvmStatic
        fun getDatabase(context : Context) : FavoriteDatabase {
            if (INSTANCE == null) {
                synchronized(FavoriteDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        FavoriteDatabase::class.java, "note_database")
                        .build()
                }
            }
            return INSTANCE as FavoriteDatabase
        }
    }
}