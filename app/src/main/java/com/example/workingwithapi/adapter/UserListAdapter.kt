package com.example.workingwithapi.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.workingwithapi.data.api.modal.Data
import com.example.workingwithapi.databinding.UserItemBinding

class UserListAdapter : RecyclerView.Adapter<UserListAdapter.UserListViewHolder>() {





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
    var userDataResponses : List<Data>



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



        holder.binding.apply {
            val userItem  = userDataResponses[position]
            tvFirstName.text = userItem.first_name
            tvLastName.text = userItem.last_name
            tvEmail.text = userItem.email
        }
        Log.d("rvItems",userDataResponses.size.toString())


        //code for imageview to add favorites

    }

    override fun getItemCount() = userDataResponses.size
}