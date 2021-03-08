package com.example.workingwithapi.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.workingwithapi.R
import com.example.workingwithapi.data.api.modal.StorageFile
import com.example.workingwithapi.data.api.modal.StorageImage
import com.example.workingwithapi.listener.OnFileClickListener
import com.example.workingwithapi.listener.OnImageClickListener

class FileListAdapter(private val context: Context, private val fileList : ArrayList<StorageFile>, val onFileClickListener: OnFileClickListener)  : RecyclerView.Adapter<FileListAdapter.FileViewHolder>(){
    inner class FileViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        var file : ImageView = itemView.findViewById(R.id.row_image)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.custom_row_image,parent,false)

        return FileViewHolder(view)
    }

    override fun onBindViewHolder(holder: FileViewHolder, position: Int) {

        val currentImage = fileList[position]
        Glide.with(context).load(currentImage.filePath).centerCrop().placeholder(R.drawable.pdf).into(holder.file)

        Toast.makeText(context,"${fileList.size}", Toast.LENGTH_LONG).show()
        holder.itemView.setOnClickListener {
            onFileClickListener.onClick(currentImage)
        }

    }

    override fun getItemCount(): Int {

        return fileList.size
    }


}