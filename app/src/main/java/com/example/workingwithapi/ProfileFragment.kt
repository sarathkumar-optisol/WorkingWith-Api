package com.example.workingwithapi

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.workingwithapi.databinding.FragmentHomeBinding
import com.example.workingwithapi.databinding.FragmentProfileBinding
import com.example.workingwithapi.databinding.FragmentSettingsBinding
import com.example.workingwithapi.main.MainViewModel
import kotlinx.coroutines.flow.collect


class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private lateinit var binding : FragmentProfileBinding

    private lateinit var viewModel: MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = activity?.run {
            ViewModelProviders.of(this)[MainViewModel::class.java]
        } ?: throw Exception("Invalid Activity")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding = FragmentProfileBinding.bind(view)



        viewModel.UserProfile()



        lifecycleScope.launchWhenStarted {
            viewModel.UserProfile.collect{ event ->
                when(event){
                    is MainViewModel.UserProfileEvent.Success ->{
                       // binding.progressBar.isVisible = false

                        binding.tvUserName.text =" ${event.resultText.first_name }  ${event.resultText.last_name }"
                        binding.tvUserEmail.text = event.resultText.email
                        Glide.with(context!!).load(event.resultText.avatar).circleCrop().into(binding.ivUserProfile)




                        }
                    }
//                    is MainViewModel.UserListEvent.Failure ->{
//                        //Toast.makeText(this@HomeActivity,"No Network",Toast.LENGTH_SHORT).show()
//                        binding.progressBar.isVisible = false
//                    }
//                    is MainViewModel.UserListEvent.Loading -> {
//
//                        binding.progressBar.isVisible = true
//
//                    }
//                    else -> Unit
                }

            }
        }


    }

