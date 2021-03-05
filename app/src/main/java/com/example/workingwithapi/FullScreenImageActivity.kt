package com.example.workingwithapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.workingwithapi.databinding.ActivityFullScreenImageBinding
import com.example.workingwithapi.databinding.ActivitySingleUserProfileBinding

class FullScreenImageActivity : AppCompatActivity() {

    private lateinit var binding : ActivityFullScreenImageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFullScreenImageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val name = intent.getStringExtra("EXTRA_NAME")
        val lastname = intent.getStringExtra("EXTRA_IMAGE_PATH")


        Glide.with(this).load(lastname).centerCrop().into(binding.ivFullImage)
    }
}