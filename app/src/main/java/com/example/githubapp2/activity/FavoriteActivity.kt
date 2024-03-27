package com.example.githubapp2.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubapp2.activity.adapter.GithubAdapter
import com.example.githubapp2.data.response.ItemsItem
import com.example.githubapp2.databinding.ActivityFavoriteBinding
import com.example.githubapp2.viewmodel.FavoriteViewModel
import com.example.githubapp2.viewmodel.factory.FavoriteViewModelFactory

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding : ActivityFavoriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.rvFavorite.layoutManager = layoutManager
        val adapter = GithubAdapter()

        val favoriteViewModel = obtainViewModel(this@FavoriteActivity)
        favoriteViewModel.getAllFavoriteUser().observe(this){
            val favoriteUsers = arrayListOf<ItemsItem>()
            it.map{
                val item = ItemsItem(login = it.username, avatarUrl = it.avatarUrl)
                favoriteUsers.add(item)
            }
            adapter.submitList(favoriteUsers)
        }
        binding.rvFavorite.adapter = adapter
    }

    private fun obtainViewModel(activity: AppCompatActivity) : FavoriteViewModel {
        val factory = FavoriteViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(FavoriteViewModel::class.java)
    }
}