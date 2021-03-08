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
import com.example.workingwithapi.adapter.VideoListAdapter
import com.example.workingwithapi.data.api.modal.StorageImage
import com.example.workingwithapi.data.api.modal.StorageVideo
import com.example.workingwithapi.databinding.FragmentImageBinding
import com.example.workingwithapi.databinding.FragmentVideoBinding
import com.example.workingwithapi.listener.OnImageClickListener
import com.example.workingwithapi.listener.OnVideoClickListener
import java.lang.Exception


class VideoFragment : Fragment(R.layout.fragment_video) {

    private lateinit var binding: FragmentVideoBinding

    private lateinit var allVideos : ArrayList<StorageVideo>


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentVideoBinding.bind(view)

        binding.rvVideos.layoutManager = GridLayoutManager(context,3)
        binding.rvVideos.setHasFixedSize(true)



        allVideos = ArrayList()

        if (allVideos.isEmpty()) {

            allVideos = getVideos()

            binding.rvVideos.adapter = VideoListAdapter(activity!!,allVideos, object  : OnVideoClickListener {
                override fun onClick(image: StorageVideo) {
                    val name  = image.videoName
                    val lastname = image.videoPath

                    Intent(activity, FullScreenImageActivity::class.java).also {
                        it.putExtra("EXTRA_NAME",name)
                        it.putExtra("EXTRA_PATH",lastname)
                        startActivity(it)
                    }
                }

            })


        }


    }



    private fun getVideos() : ArrayList<StorageVideo>{

        val video = ArrayList<StorageVideo>()

        val allVideoUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI

        val projection = arrayOf(MediaStore.Video.VideoColumns.DATA, MediaStore.Video.Media.DISPLAY_NAME)

        val cursor = activity!!.contentResolver.query(allVideoUri,projection,null,null,null)


        try {

            cursor!!.moveToFirst()
            do {
                val storageVideo = StorageVideo(videoName = "",videoPath = "")
                storageVideo.videoPath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA))
                storageVideo.videoName = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME))
                video.add(storageVideo)
                //Log.d("images","$video")
            }while (cursor.moveToNext())
            cursor.close()


        }catch (e : Exception){
            e.printStackTrace()
        }

        return video
    }

}