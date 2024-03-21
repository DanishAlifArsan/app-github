package com.example.githubapp2.activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubapp2.activity.adapter.GithubAdapter
import com.example.githubapp2.data.response.ItemsItem
import com.example.githubapp2.databinding.FragmentFollowingBinding
import com.example.githubapp2.viewmodel.FollowingViewModel

class FollowingFragment : Fragment() {
    companion object {
        const val ARG_SECTION_NUMBER = "section_number"
        const val ARG_NAME = "user_name"
    }

    private lateinit var binding : FragmentFollowingBinding
    private lateinit var username : String
    private var position : Int?= null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFollowingBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val followingViewModel = ViewModelProvider(requireActivity(), ViewModelProvider.NewInstanceFactory()).get(FollowingViewModel::class.java)
        arguments?.let {
            position = it.getInt(ARG_SECTION_NUMBER)
            username = it.getString(ARG_NAME).toString()
        }

        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvUser.layoutManager = layoutManager

        if (position == 1){
            followingViewModel.showFollower(username)
            followingViewModel.follower.observe(viewLifecycleOwner) {
                setFollowingData(it)
            }
        } else {
            followingViewModel.showFollowing(username)
            followingViewModel.following.observe(viewLifecycleOwner) {
                setFollowingData(it)
            }
        }

        followingViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        followingViewModel.toastText.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let {
                Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setFollowingData(user : List<ItemsItem>) {
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