package com.example.githubapp2.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.githubapp2.data.preference.SettingPreferences
import kotlinx.coroutines.launch

class ThemeViewModel (private val pref: SettingPreferences) : ViewModel() {
    fun getThemeString() : LiveData<Boolean> {
        return pref.getThemeString().asLiveData()
    }

    fun saveThemeString(isDarkModeActive : Boolean) {
        viewModelScope.launch {
            pref.saveThemeSetting(isDarkModeActive)
        }
    }
}