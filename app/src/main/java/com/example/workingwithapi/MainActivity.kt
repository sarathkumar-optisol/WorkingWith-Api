package com.example.workingwithapi

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.getSystemService
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.example.workingwithapi.databinding.ActivityMainBinding
import com.example.workingwithapi.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect


private const val TAG = "MainActivity"

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


    private lateinit var binding : ActivityMainBinding
    private val viewModel : MainViewModel by viewModels()

    lateinit var sharedPreferences: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()




        sharedPreferences = getSharedPreferences("token" , Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        loadSharedPreference()

        binding.btnSignin.setOnClickListener {

            val email = binding.etName.text.toString()
            val password = binding.etPassword.text.toString()
                hideKeyboard(binding.btnSignin)
            if(binding.etName.text?.isEmpty()!! || binding.etPassword.text?.isEmpty()!!){
                Toast.makeText(this,"Enter Valid email or password",Toast.LENGTH_SHORT).show()
                binding.progressBar.isVisible = false
            }else{
                viewModel.login(email, password
                )


            }

        }
        binding.clMainActivity.setOnClickListener {
            hideKeyboard(binding.clMainActivity)
        }



        lifecycleScope.launchWhenStarted {
            viewModel.Login.collect{ event ->
                when(event){
                        is MainViewModel.LoginEvent.Success ->{
                            //binding.tvText.text = event.resultText
                            Toast.makeText(this@MainActivity,"Logged In",Toast.LENGTH_SHORT).show()


                            binding.progressBar.isVisible = false
                            Log.d(TAG, event.result)
                            editor.apply{
                                putString("token", event.result)
                                apply()
                            }

                            val intent = Intent(this@MainActivity,HomeActivity::class.java)
                            startActivity(intent)
                            finish()


                        }
                        is MainViewModel.LoginEvent.Failure ->{
                            //binding.tvText.text = event.errorText
                            binding.progressBar.isVisible = false
                            Toast.makeText(this@MainActivity,"No Network",Toast.LENGTH_SHORT).show()
                            Log.d(TAG, event.error)

                        }
                    is MainViewModel.LoginEvent.Loading -> {
                        binding.progressBar.isVisible = true

                    }
                    else -> Unit
                }

            }
        }
    }

    fun loadSharedPreference(){
        val token = sharedPreferences.getString("token",null)

        if (token!=null){
            val intent = Intent(this@MainActivity,HomeActivity::class.java)
            startActivity(intent)
        }
    }

    private fun hideKeyboard(view : View){
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken,0)
    }
}