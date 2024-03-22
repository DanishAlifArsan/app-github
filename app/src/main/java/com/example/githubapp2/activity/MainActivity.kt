package com.example.githubapp2.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubapp2.activity.adapter.GithubAdapter
import com.example.githubapp2.data.response.ItemsItem
import com.example.githubapp2.databinding.ActivityMainBinding
import com.example.githubapp2.viewmodel.ListViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val listViewModel : ListViewModel by viewModels()
    private var previousSearch = "1"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { textView, actionId, event ->
                    searchView.hide()
                    val searchQueue = if (searchView.text.isBlank()) previousSearch else searchView.text.toString()
                    listViewModel.showUsers(searchQueue)
                    previousSearch = searchQueue
                    searchBar.setText(previousSearch)
                    false
                }
        }

        val layoutManager = LinearLayoutManager(this)
        binding.rvUser.layoutManager = layoutManager

        listViewModel.user.observe(this){
            setUserData(it)
        }

        listViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        listViewModel.toastText.observe(this) {
            it.getContentIfNotHandled()?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setUserData(user : List<ItemsItem?>?) {
        val adapter = GithubAdapter()
        adapter.submitList(user)
        binding.rvUser.adapter = adapter
    }

    private fun showLoading(isLoading : Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }


}