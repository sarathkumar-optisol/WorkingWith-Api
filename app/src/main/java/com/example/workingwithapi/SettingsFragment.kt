package com.example.workingwithapi

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.workingwithapi.databinding.FragmentHomeBinding
import com.example.workingwithapi.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private lateinit var binding : FragmentSettingsBinding

    private lateinit var sharedPreference : SharedPreferences

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentSettingsBinding.bind(view)



        sharedPreference = this.activity!!.getSharedPreferences("token", Context.MODE_PRIVATE)




        binding.btnLogOut.setOnClickListener {
            sharedPreference.edit().remove("token").commit()
            val intent = Intent(activity,MainActivity::class.java)
                    startActivity(intent)
                    activity?.finish()

        }

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


    }



}