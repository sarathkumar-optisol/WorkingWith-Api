package com.example.workingwithapi.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.workingwithapi.listener.OnItemClickListener
import com.example.workingwithapi.data.api.modal.Data
import com.example.workingwithapi.databinding.UserItemBinding

class UserListAdapter(private val context: Context , val onItemClickListener: OnItemClickListener) : RecyclerView.Adapter<UserListAdapter.UserListViewHolder>() {





    inner class UserListViewHolder(val binding: UserItemBinding) : RecyclerView.ViewHolder(binding.root)


    private val differCallback = object : DiffUtil.ItemCallback<Data>(){
        override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this,differCallback)
    var userDataResponses : MutableList<Data>



    get()=differ.currentList
    set(value) {differ.submitList(value)}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListViewHolder {
        return UserListViewHolder(UserItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: UserListViewHolder, position: Int) {


        val userItem  = userDataResponses[position]
        holder.binding.apply {

            tvFirstName.text = userItem.first_name
            tvLastName.text = userItem.last_name
            tvEmail.text = userItem.email
            Glide.with(context).load(userItem.avatar).circleCrop().into(ivAvatar)

        }
        Log.d("rvItems",userDataResponses.size.toString())

        holder.itemView.setOnClickListener {
            onItemClickListener.onClick(userItem)
        }


    }

    override fun getItemCount() = userDataResponses.size
}