package com.example.githubapp2.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.example.githubapp2.data.response.GithubUserResponse
import com.example.githubapp2.databinding.ActivityUserBinding
import com.example.githubapp2.viewmodel.UserViewModel
import com.google.android.material.tabs.TabLayoutMediator

class UserActivity : AppCompatActivity() {
    private lateinit var binding : ActivityUserBinding
    private val userViewModel : UserViewModel by viewModels()

    companion object{
        const val USER_DATA = "user_data"
        private val TAB_TITLES = arrayOf(
            "Follower",
            "Following"
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = intent.getStringExtra(USER_DATA).toString()
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
    }

    private fun setUserData(user : GithubUserResponse) {
        binding.tvName.text = user.login
        Glide.with(this)
            .load(user.avatarUrl)
            .into(binding.ivPhoto)
        val sectionPagerAdapter = SectionPagerAdapter(this)
        sectionPagerAdapter.username = user.login.toString()
        binding.viewPager.adapter = sectionPagerAdapter
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = TAB_TITLES[position]
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