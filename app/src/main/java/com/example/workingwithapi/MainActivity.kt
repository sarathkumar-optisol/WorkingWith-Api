package com.example.workingwithapi

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.workingwithapi.databinding.ActivityMainBinding
import com.example.workingwithapi.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect


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
                Log.d("Value", binding.etEmail.text.toString())
                Log.d("Value", binding.etPassword.text.toString())
                //binding.progressBar.isVisible = false
                //binding.tvResult.setTextColor(Color.RED)
                //binding.tvResult.setText("Enter Amount")
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
                            //binding.tvText.setTextColor(Color.BLACK)
                            binding.tvText.text = event.resultText
                        }
                        is MainViewModel.LoginEvent.Failure ->{
                            //binding.tvText.setTextColor(Color.BLACK)
                            binding.tvText.text = event.errorText
                        }
                    is MainViewModel.LoginEvent.Loading -> {

                    }
                    else -> Unit
                }

            }
        }
    }
}