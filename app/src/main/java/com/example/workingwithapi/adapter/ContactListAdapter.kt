package com.example.workingwithapi.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.workingwithapi.R
import com.example.workingwithapi.data.api.modal.Contact
import com.example.workingwithapi.data.api.modal.StorageFile
import com.example.workingwithapi.listener.OnFileClickListener

class ContactListAdapter(private val context: Context, private val contactList : MutableList<Contact>)  : RecyclerView.Adapter<ContactListAdapter.ContactViewHolder>(){
    inner class ContactViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        var name : TextView = itemView.findViewById(R.id.tvName)
        var number : TextView = itemView.findViewById(R.id.tvNumber)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.contact_item,parent,false)

        return ContactViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {

        val currentContact = contactList[position]
        holder.name.text = currentContact.name
        holder.number.text = currentContact.number


    }

    override fun getItemCount(): Int {

        return contactList.size
    }


}