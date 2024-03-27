package com.example.githubapp2.preference

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<androidx.datastore.preferences.core.Preferences> by preferencesDataStore(name = "setting")

class SettingPreferences private constructor(private val dataStore: DataStore<Preferences>){
    companion object {
        @Volatile
        private var INSTANCE : SettingPreferences?= null

        fun getInstance(dataStore: DataStore<Preferences>) : SettingPreferences{
            return INSTANCE?: synchronized(this) {
                val instance = SettingPreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }

    private val THEME_KEY = booleanPreferencesKey("theme_setting")

    fun getThemeString() : Flow<Boolean> {
        return dataStore.data.map {
            it[THEME_KEY] ?: false
        }
    }

    suspend fun saveThemeSetting(isDarkModeActive : Boolean) {
        dataStore.edit {
            it[THEME_KEY] = isDarkModeActive
        }
    }
}