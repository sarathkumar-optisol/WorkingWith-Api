package com.example.workingwithapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.workingwithapi.adapter.ViewPagerAdapter
import com.example.workingwithapi.databinding.ActivityHomeBinding
import com.example.workingwithapi.databinding.ActivityMediaBinding
import com.example.workingwithapi.fragments.FileFragment
import com.example.workingwithapi.fragments.ImageFragment
import com.example.workingwithapi.fragments.VideoFragment
import com.google.android.material.tabs.TabLayoutMediator

class MediaActivity : AppCompatActivity() {

    lateinit var binding: ActivityMediaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMediaBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val fragments : ArrayList<Fragment> = arrayListOf(

            ImageFragment(),
            VideoFragment(),
            FileFragment()
        )

        val adapter = ViewPagerAdapter(fragments , this)
        binding.vpHome.adapter = adapter


        TabLayoutMediator(binding.tabLayout , binding.vpHome) { tab , position ->
            if (position == 0){
                tab.text = "Images"
            }else if (position == 1){
                tab.text = "Videos"
            }else{
                tab.text = "Files"
            }
        }.attach()

    }
}