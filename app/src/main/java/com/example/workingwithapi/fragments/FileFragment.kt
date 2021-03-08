package com.example.workingwithapi.fragments

import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.net.toUri
import androidx.recyclerview.widget.GridLayoutManager
import com.example.workingwithapi.FullScreenImageActivity
import com.example.workingwithapi.R
import com.example.workingwithapi.adapter.FileListAdapter
import com.example.workingwithapi.adapter.ImageListAdapter
import com.example.workingwithapi.adapter.VideoListAdapter
import com.example.workingwithapi.data.api.modal.StorageFile
import com.example.workingwithapi.data.api.modal.StorageImage
import com.example.workingwithapi.data.api.modal.StorageVideo
import com.example.workingwithapi.databinding.FragmentFileBinding
import com.example.workingwithapi.databinding.FragmentImageBinding
import com.example.workingwithapi.databinding.FragmentVideoBinding
import com.example.workingwithapi.listener.OnFileClickListener
import com.example.workingwithapi.listener.OnImageClickListener
import com.example.workingwithapi.listener.OnVideoClickListener
import java.lang.Exception


class FileFragment : Fragment(R.layout.fragment_file) {


    private lateinit var binding: FragmentFileBinding

    private lateinit var allFiles : ArrayList<StorageFile>


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentFileBinding.bind(view)

        binding.rvFiles.layoutManager = GridLayoutManager(context,3)
        binding.rvFiles.setHasFixedSize(true)



        allFiles = ArrayList()

        if (allFiles.isEmpty()) {

            allFiles = getFiles()


            binding.rvFiles.adapter = FileListAdapter(activity!!,allFiles, object  : OnFileClickListener {
                override fun onClick(file: StorageFile) {
                    val name  = file.fileName
                    val lastname = file.filePath

                    Intent(activity, FullScreenImageActivity::class.java).also {
                        it.putExtra("EXTRA_NAME",name)
                        it.putExtra("EXTRA_PATH",lastname)
                        startActivity(it)
                    }
                }

            })


        }


    }



    private fun getFiles() : ArrayList<StorageFile>{



        val file = ArrayList<StorageFile>()

        val allfileuri = MediaStore.Files.getContentUri("external")

        val projection = arrayOf(MediaStore.Files.FileColumns.DATA,MediaStore.Files.FileColumns.DISPLAY_NAME)

        val cursor = activity!!.contentResolver.query(allfileuri,projection,null,null,null)


        try {

            cursor!!.moveToFirst()
            do {
                val storageFile = StorageFile(fileName = "",filePath = "")
                storageFile.filePath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATA))
                storageFile.fileName = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DISPLAY_NAME))

                file.add(storageFile)


                Log.d("images","$file")
            }while (cursor.moveToNext())
            cursor.close()


        }catch (e : Exception){
            e.printStackTrace()
        }

        return file
    }

}