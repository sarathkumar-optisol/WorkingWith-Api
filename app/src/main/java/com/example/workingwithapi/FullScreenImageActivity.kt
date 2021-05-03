package com.example.workingwithapi

import android.content.ContentValues
import android.graphics.Bitmap
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.core.graphics.createBitmap
import androidx.core.view.drawToBitmap
import com.bumptech.glide.Glide
import com.example.workingwithapi.databinding.ActivityFullScreenImageBinding
import com.example.workingwithapi.databinding.ActivitySingleUserProfileBinding
import java.io.File
import java.io.OutputStream
import java.util.*

class FullScreenImageActivity : AppCompatActivity() {

    private lateinit var binding : ActivityFullScreenImageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFullScreenImageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val name = intent.getStringExtra("EXTRA_NAME")
        val lastname = intent.getStringExtra("EXTRA_IMAGE_PATH")


        Glide.with(this).load(lastname).centerCrop().into(binding.ivFullImage)



        var btm = binding.ivFullImage.drawToBitmap()
        saveImage(btm)
    }

    private fun saveImage(btm: Bitmap) {
        val fos : OutputStream

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
                val resolver = contentResolver
                val contentValues = ContentValues()
                contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME,"Image"+".jpg")

                contentValues.put(MediaStore.MediaColumns.MIME_TYPE,"image/jpg")

                contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH,Environment.DIRECTORY_PICTURES+File.separator+"CacheFolder1")

                val imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,contentValues)

                fos = resolver.openOutputStream(Objects.requireNonNull(imageUri)!!)!!

                btm.compress(Bitmap.CompressFormat.JPEG,100,fos)

                Objects.requireNonNull<OutputStream>(fos)

                Toast.makeText(this,"Savesd",Toast.LENGTH_SHORT).show()

            }

        }catch (e:Exception){

        }
    }


}
