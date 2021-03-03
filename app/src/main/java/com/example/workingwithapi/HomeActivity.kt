package com.example.workingwithapi

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.opengl.Visibility
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.workingwithapi.adapter.UserListAdapter
import com.example.workingwithapi.adapter.ViewPagerAdapter
import com.example.workingwithapi.data.api.modal.Data
import com.example.workingwithapi.data.api.modal.UserListResponse
import com.example.workingwithapi.databinding.ActivityHomeBinding
import com.example.workingwithapi.databinding.FragmentHomeBinding
import com.example.workingwithapi.main.MainViewModel
import com.example.workingwithapi.others.Constants.QUERY_PAGE_SIZE
import com.example.workingwithapi.util.DispatcherProvider
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {


    lateinit var sharedPreferences : SharedPreferences

     lateinit var binding: ActivityHomeBinding


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        supportActionBar?.setDisplayHomeAsUpEnabled(true)

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
    }

    override fun onBackPressed() {
        if (binding.vpHome.currentItem == 0){
            super.onBackPressed()
        }else{
            binding.vpHome.currentItem = binding.vpHome.currentItem - 1
        }
    }




}
