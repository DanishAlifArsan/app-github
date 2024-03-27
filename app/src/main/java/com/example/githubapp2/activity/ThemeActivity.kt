package com.example.githubapp2.activity

import android.os.Bundle
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.example.githubapp2.R
import com.example.githubapp2.preference.SettingPreferences
import com.example.githubapp2.preference.dataStore
import com.example.githubapp2.viewmodel.ThemeViewModel
import com.example.githubapp2.viewmodel.factory.ThemeViewModelFactory
import com.google.android.material.switchmaterial.SwitchMaterial

class ThemeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_theme)

        val switchTheme : SwitchMaterial = findViewById(R.id.switch_theme)

        val pref = SettingPreferences.getInstance(application.dataStore)
        val themeViewModel = ViewModelProvider(this, ThemeViewModelFactory(pref)).get(
            ThemeViewModel::class.java
        )

        themeViewModel.getThemeString().observe(this) {
            if (it) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                switchTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                switchTheme.isChecked = false
            }
        }

        switchTheme.setOnCheckedChangeListener{ _: CompoundButton?, isChecked : Boolean ->
            themeViewModel.saveThemeString(isChecked)
        }
    }
}