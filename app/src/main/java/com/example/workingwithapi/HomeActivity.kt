package com.example.workingwithapi

import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.workingwithapi.adapter.ViewPagerAdapter
import com.example.workingwithapi.databinding.ActivityHomeBinding
import com.example.workingwithapi.fragments.HomeFragment
import com.example.workingwithapi.fragments.ProfileFragment
import com.example.workingwithapi.fragments.SettingsFragment
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {


    lateinit var sharedPreferences : SharedPreferences

     lateinit var binding: ActivityHomeBinding


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        //supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)



 //       sharedPreferences = getSharedPreferences("token", Context.MODE_PRIVATE)
//
//        var token = sharedPreferences.getString("token", null)
//        Log.d("token","${token.toString()}")

//        binding.navView.setNavigationItemSelectedListener {
//            when(it.itemId){
//                R.id.logOut -> {
//                    sharedPreferences.edit().remove("token").commit()
//                    Toast.makeText(this,"Logged Out", Toast.LENGTH_SHORT).show()
//                    val intent = Intent(this,MainActivity::class.java)
//                    startActivity(intent)
//                    finish()
//                }
//            }
//            true
//        }


        val fragments : ArrayList<Fragment> = arrayListOf(

            HomeFragment(),
                ProfileFragment(),
            SettingsFragment()
        )

        val adapter = ViewPagerAdapter(fragments , this)
        binding.vpHome.adapter = adapter


        TabLayoutMediator(binding.tabLayout , binding.vpHome) { tab , position ->
            if (position == 0){
                tab.text = "UserList"
            }else if (position == 1){
                tab.text = "UserProfile"
            }else{
                tab.text = "Settings"
            }
        }.attach()
    }

    override fun onBackPressed() {
        if (binding.vpHome.currentItem == 0){
            super.onBackPressed()
        }else{
            binding.vpHome.currentItem = binding.vpHome.currentItem - 1
        }
    }




}
