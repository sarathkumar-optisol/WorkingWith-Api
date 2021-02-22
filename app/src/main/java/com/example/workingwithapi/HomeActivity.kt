package com.example.workingwithapi

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
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




import dagger.hilt.EntryPoint


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


        binding.progressBar.isVisible = false
        viewModel.userList()
        setupRecyclerView()

        lifecycleScope.launchWhenStarted {
            viewModel.UserList.collect{ event ->
                when(event){
                    is MainViewModel.UserListEvent.Success ->{
                        binding.progressBar.isVisible = false
                        //binding.tvText.text = event.resultText
                        //Toast.makeText(this@MainActivity,"Logged In",Toast.LENGTH_SHORT).show()
                       userListAdapter.userDataResponses  = event.resultText
                        Log.d("Home",event.toString())


                    }
                    is MainViewModel.UserListEvent.Failure ->{
                        binding.progressBar.isVisible = false
                        //binding.tvText.text = event.errorText
//                        Toast.makeText(this@MainActivity,"Enter Valid Name or Password failure",Toast.LENGTH_SHORT).show()
//                        Log.d(TAG, event.error)

                    }
                    is MainViewModel.UserListEvent.Loading -> {

                        binding.progressBar.isVisible = true

                    }
                    else -> Unit
                }

            }
        }



    }


    private fun setupRecyclerView() = binding.rvUserList.apply{

        userListAdapter = UserListAdapter(context)
        adapter = userListAdapter
        layoutManager = LinearLayoutManager(this@HomeActivity)

    }





}