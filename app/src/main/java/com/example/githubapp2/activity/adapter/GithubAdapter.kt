package com.example.githubapp2.activity.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubapp2.activity.UserActivity
import com.example.githubapp2.data.response.ItemsItem
import com.example.githubapp2.databinding.GithubListLayoutBinding

class GithubAdapter : ListAdapter<ItemsItem, GithubAdapter.MyViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = GithubListLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val review = getItem(position)
        holder.bind(holder, review)
    }
    class MyViewHolder(val binding: GithubListLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(holder: MyViewHolder, user: ItemsItem){
            binding.tvUsername.text = "${user.login}"
            Glide.with(holder.itemView.context)
                .load(user.avatarUrl)
                .into(binding.ivProfile)
            binding.constraintLayout.setOnClickListener {
                val i = Intent(holder.itemView.context, UserActivity::class.java)
                i.putExtra(UserActivity.USER_DATA, user.login)
                i.putExtra(UserActivity.AVATAR_DATA, user.avatarUrl)
                holder.itemView.context.startActivity(i)
            }
        }
    }
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ItemsItem>() {
            override fun areItemsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}