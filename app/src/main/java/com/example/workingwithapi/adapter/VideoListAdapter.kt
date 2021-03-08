package com.example.workingwithapi.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.workingwithapi.R
import com.example.workingwithapi.data.api.modal.StorageImage
import com.example.workingwithapi.data.api.modal.StorageVideo
import com.example.workingwithapi.listener.OnImageClickListener
import com.example.workingwithapi.listener.OnVideoClickListener

class VideoListAdapter(private val context: Context, private val videoList : ArrayList<StorageVideo>, val onvideoClickListener: OnVideoClickListener)  : RecyclerView.Adapter<VideoListAdapter.VideoViewHolder>(){
    inner class VideoViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        var video : ImageView = itemView.findViewById(R.id.row_image)
        //check here if any error
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.custom_row_image,parent,false)

        return VideoViewHolder(view)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {

        val currentVideo = videoList[position]
        Glide.with(context).load(currentVideo.videoPath).centerCrop().into(holder.video)

        //Toast.makeText(context,"${videoList.size}", Toast.LENGTH_LONG).show()
        holder.itemView.setOnClickListener {
            onvideoClickListener.onClick(currentVideo)
        }

    }

    override fun getItemCount(): Int {

        return videoList.size
    }


}