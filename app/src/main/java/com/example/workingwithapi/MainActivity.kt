package com.example.workingwithapi

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.workingwithapi.databinding.ActivityMainBinding
import com.example.workingwithapi.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect


const val TAG = "MainActivity"

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


    private lateinit var binding : ActivityMainBinding
    private val viewModel : MainViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnLogin.setOnClickListener {

            if(binding.etEmail.text?.isEmpty()!! || binding.etPassword.text?.isEmpty()!!){
                Toast.makeText(this,"Enter Valid email or password",Toast.LENGTH_SHORT).show()
            }else{
                viewModel.login(
                    binding.etEmail.text.toString(),
                    binding.etPassword.text.toString()
                )
            }

        }

        lifecycleScope.launchWhenStarted {
            viewModel.Login.collect{ event ->
                when(event){
                        is MainViewModel.LoginEvent.Success ->{
                            binding.tvText.text = event.resultText
                            Log.d(TAG, event.resultText)
                        }
                        is MainViewModel.LoginEvent.Failure ->{
                            binding.tvText.text = event.errorText
                            Log.d(TAG, event.errorText)

                        }
                    is MainViewModel.LoginEvent.Loading -> {

                    }
                    else -> Unit
                }

            }
        }
    }
}