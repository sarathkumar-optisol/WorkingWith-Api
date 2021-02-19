package com.example.workingwithapi

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.workingwithapi.adapter.UserListAdapter
import com.example.workingwithapi.data.api.LoginApi
import com.example.workingwithapi.data.api.modal.Data
import com.example.workingwithapi.databinding.ActivityHomeBinding
import com.example.workingwithapi.databinding.ActivityMainBinding
import com.example.workingwithapi.main.MainViewModel
import com.example.workingwithapi.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import okhttp3.internal.notify
import retrofit2.HttpException
import java.io.IOException

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {


    private lateinit var binding : ActivityHomeBinding
    private val viewModel : MainViewModel by viewModels()
    private lateinit var userListAdapter: UserListAdapter

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)



        viewModel.userList()
        setupRecyclerView()


        lifecycleScope.launchWhenStarted {


            viewModel.UserList.collect{ event ->
                when(event){
                    is MainViewModel.UserListEvent.Success ->{
                        //binding.tvText.text = event.resultText
                        userListAdapter.userDataResponses = listOf(event.resultText)

                        Log.d("home",event.resultText.first_name)


                    }
                    is MainViewModel.UserListEvent.Failure ->{

                    }

                    else -> Unit
                }

            }
        }



    }

    private fun setupRecyclerView() = binding.rvUserList.apply{

            userListAdapter = UserListAdapter()
        adapter = userListAdapter
        layoutManager = LinearLayoutManager(this@HomeActivity)

    }
}