package com.example.workingwithapi.fragments

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.workingwithapi.FullScreenImageActivity
import com.example.workingwithapi.R
import com.example.workingwithapi.adapter.ImageListAdapter
import com.example.workingwithapi.data.api.modal.StorageFile
import com.example.workingwithapi.data.api.modal.StorageImage
import com.example.workingwithapi.databinding.FragmentFileBinding
import com.example.workingwithapi.databinding.FragmentImageBinding
import com.example.workingwithapi.listener.OnImageClickListener
import java.lang.Exception


class FileFragment : Fragment(R.layout.fragment_file) {


//    private lateinit var binding: FragmentFileBinding
//
//    private lateinit var allFiles : ArrayList<StorageFile>
//
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        binding = FragmentFileBinding.bind(view)
//
//        binding.rvFiles.layoutManager = GridLayoutManager(context,3)
//        binding.rvFiles.setHasFixedSize(true)
//
//
//
//        allFiles = ArrayList()
//
//        if (allFiles.isEmpty()) {
//
//            allFiles = getVideos()
//
//            binding.rvFiles.adapter = FileListAdapter(activity!!,allFiles, object  : OnFileClickListener {
//                override fun onClick(file: StorageFile) {
//                    val name  = file.fileName
//                    val lastname = file.filePath
//
//                    Intent(activity, FullScreenImageActivity::class.java).also {
//                        it.putExtra("EXTRA_NAME",name)
//                        it.putExtra("EXTRA_PATH",lastname)
//                        startActivity(it)
//                    }
//                }
//
//            })
//
//
//        }
//
//
//    }
//
//
//
//    private fun getVideos() : ArrayList<StorageFile>{
//
//        val files = ArrayList<StorageFile>()
//
//
//        val allFileUri = MediaStore.Files.getContentUri(MediaStore.Images.Media.EXTERNAL_CONTENT_URI.toString())
//
//        val projection = arrayOf(MediaStore.Files.FileColumns.DATA, MediaStore.Files.getContentUri(MediaStore.Images.Media.DISPLAY_NAME))
//
//        val cursor = activity!!.contentResolver.query(allFileUri,projection,null,null,null)
//
//
//        try {
//
//            cursor!!.moveToFirst()
//            do {
//                val storageFile = StorageImage(imagePath = "",imageName = "")
//                storageImage.imagePath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA))
//                storageImage.imageName = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME))
//                images.add(storageImage)
//                Log.d("images","$images")
//            }while (cursor.moveToNext())
//            cursor.close()
//
//
//        }catch (e : Exception){
//            e.printStackTrace()
//        }
//
//        return images
//    }
}