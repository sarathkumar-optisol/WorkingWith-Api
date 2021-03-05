package com.example.workingwithapi.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.workingwithapi.FullScreenImageActivity
import com.example.workingwithapi.R
import com.example.workingwithapi.data.api.modal.StorageImage
import com.example.workingwithapi.listener.OnImageClickListener
import com.example.workingwithapi.listener.OnItemClickListener

class ImageListAdapter(private val context: Context , private val imagesList : ArrayList<StorageImage>, val onImageClickListener: OnImageClickListener)  : RecyclerView.Adapter<ImageListAdapter.ImageViewHolder>(){
    inner class ImageViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        var image : ImageView = itemView.findViewById(R.id.row_image)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.custom_row_image,parent,false)

        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {

        val currentImage = imagesList[position]
        Glide.with(context).load(currentImage.imagePath).centerCrop().into(holder.image)

        Toast.makeText(context,"${imagesList.size}",Toast.LENGTH_LONG).show()
        holder.itemView.setOnClickListener {
            onImageClickListener.onClick(currentImage)
        }

    }

    override fun getItemCount(): Int {

        return imagesList.size
    }


}