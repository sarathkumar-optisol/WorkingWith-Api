package com.example.workingwithapi

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.workingwithapi.databinding.ActivityHomeBinding
import com.example.workingwithapi.databinding.ActivitySingleUserProfileBinding

class SingleUserProfile : AppCompatActivity() {

    private lateinit var binding : ActivitySingleUserProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySingleUserProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val name = intent.getStringExtra("EXTRA_NAME")
        val lastname = intent.getStringExtra("EXTRA_LASTNAME")
        val avatar = intent.getStringExtra("EXTRA_AVATAR")
        val email = intent.getStringExtra("EXTRA_EMAIL")


        binding.tvUserName.text = "$name  $lastname"
        Glide.with(this).load(avatar).circleCrop().into(binding.ivUserProfile)
        binding.tvUserEmail.text = email


    }
}