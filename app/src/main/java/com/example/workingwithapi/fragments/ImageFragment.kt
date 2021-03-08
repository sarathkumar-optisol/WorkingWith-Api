package com.example.workingwithapi.fragments

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.workingwithapi.FullScreenImageActivity
import com.example.workingwithapi.R
import com.example.workingwithapi.SingleUserProfile
import com.example.workingwithapi.adapter.ImageListAdapter
import com.example.workingwithapi.data.api.modal.StorageImage
import com.example.workingwithapi.databinding.FragmentImageBinding
import com.example.workingwithapi.listener.OnImageClickListener
import java.lang.Exception


class ImageFragment : Fragment(R.layout.fragment_image) {

    private lateinit var binding: FragmentImageBinding

    private lateinit var allPictures : ArrayList<StorageImage>


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentImageBinding.bind(view)

        binding.rvImages.layoutManager = GridLayoutManager(context,3)
        binding.rvImages.setHasFixedSize(true)



    allPictures = ArrayList()

        if (allPictures.isEmpty()) {

            allPictures = getImages()

            binding.rvImages.adapter = ImageListAdapter(activity!!,allPictures, object  : OnImageClickListener{
                override fun onClick(image: StorageImage) {
                    val name  = image.imageName
                    val lastname = image.imagePath

                    Intent(activity, FullScreenImageActivity::class.java).also {
                        it.putExtra("EXTRA_NAME",name)
                        it.putExtra("EXTRA_PATH",lastname)
                        startActivity(it)
                    }
                }

            })


        }


    }



    private fun getImages() : ArrayList<StorageImage>{

        val images = ArrayList<StorageImage>()

        val allImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI

        val projection = arrayOf(MediaStore.Images.ImageColumns.DATA,MediaStore.Images.Media.DISPLAY_NAME)

        val cursor = activity!!.contentResolver.query(allImageUri,projection,null,null,null)


        try {

            cursor!!.moveToFirst()
            do {
                val storageImage = StorageImage(imagePath = "",imageName = "")
                storageImage.imagePath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA))
                storageImage.imageName = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME))
                images.add(storageImage)
                //Log.d("images","$images")
            }while (cursor.moveToNext())
            cursor.close()


        }catch (e : Exception){
            e.printStackTrace()
        }

        return images
    }
}

