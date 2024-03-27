package com.example.githubapp2.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.githubapp2.R
import com.example.githubapp2.data.database.Favorite
import com.example.githubapp2.data.response.GithubUserResponse
import com.example.githubapp2.databinding.ActivityUserBinding
import com.example.githubapp2.viewmodel.FavoriteViewModel
import com.example.githubapp2.viewmodel.UserViewModel
import com.example.githubapp2.viewmodel.factory.FavoriteViewModelFactory
import com.google.android.material.tabs.TabLayoutMediator

class UserActivity : AppCompatActivity() {
    private lateinit var binding : ActivityUserBinding
    private val userViewModel : UserViewModel by viewModels()
    private var favorite : Favorite ?= null
    private var isFavorite = false

    companion object{
        const val USER_DATA = "user_data"
        const val AVATAR_DATA = "avatar_data"
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.follower,
            R.string.following
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val favoriteViewModel = obtainViewModel(this@UserActivity)

        val user = intent.getStringExtra(USER_DATA).toString()
        val avatar = intent.getStringExtra(AVATAR_DATA).toString()
        userViewModel.showUser(user)

        userViewModel.user.observe(this) {
            setUserData(it)
        }

        userViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        userViewModel.toastText.observe(this) {
            it.getContentIfNotHandled()?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        }

        favoriteViewModel.getFavoriteUserByUsername(user).observe(this) {
            if (it != null) {
                binding.fabFavorite.setImageResource(R.drawable.ic_favorite)
                favorite = it
                isFavorite = true
            } else {
                binding.fabFavorite.setImageResource(R.drawable.ic_no_favorite)
                favorite = Favorite()
                favorite?.username = user
                favorite?.avatarUrl = avatar
                isFavorite = false
            }
        }

        binding.fabFavorite.setOnClickListener {
            if (isFavorite) {
                favoriteViewModel.delete(favorite as Favorite)
                Toast.makeText(this, getString(R.string.delete), Toast.LENGTH_SHORT).show()
            } else {
                favoriteViewModel.insert(favorite as Favorite)
                Toast.makeText(this, getString(R.string.insert), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity) : FavoriteViewModel {
        val factory = FavoriteViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(FavoriteViewModel::class.java)
    }

    private fun setUserData(user : GithubUserResponse) {
        binding.tvName.text =  user.name?.toString()
        binding.tvUsername.text = user.login
        binding.tvFollower.text = "${getText(R.string.follower)} ${user.followers}"
        binding.tvFollowing.text = "${getText(R.string.following)} ${user.following}"
        Glide.with(this)
            .load(user.avatarUrl)
            .into(binding.ivPhoto)
        val sectionPagerAdapter = SectionPagerAdapter(this)
        sectionPagerAdapter.username = user.login.toString()
        binding.viewPager.adapter = sectionPagerAdapter
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    private fun showLoading(isLoading : Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}