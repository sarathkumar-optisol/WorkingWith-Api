package com.example.workingwithapi

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.util.Log
import android.widget.AbsListView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.workingwithapi.adapter.UserListAdapter
import com.example.workingwithapi.data.api.LoginApi
import com.example.workingwithapi.data.api.modal.Data
import com.example.workingwithapi.databinding.ActivityHomeBinding
import com.example.workingwithapi.databinding.ActivityMainBinding
import com.example.workingwithapi.main.MainViewModel
import com.example.workingwithapi.others.Constants.QUERY_PAGE_SIZE
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

    lateinit var userListAdapter: UserListAdapter


    var pagenumber = 1
    var pageLimit = 2
    var isLoadingCA = false
    val limit = 6



    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)




        binding.progressBar.isVisible = false
        viewModel.userList(pagenumber)
        setupRecyclerView()

        lifecycleScope.launchWhenStarted {
            viewModel.UserList.collect{ event ->
                when(event){
                    is MainViewModel.UserListEvent.Success ->{
                        binding.progressBar.isVisible = false
                       userListAdapter.userDataResponses  = event.resultText

                        //userListAdapter.differ.submitList(event.resultText.toList())


                        Log.d("Home",event.toString())
                        Log.d("Home",pagenumber.toString())
                    }
                    is MainViewModel.UserListEvent.Failure ->{
                        binding.progressBar.isVisible = false
                    }
                    is MainViewModel.UserListEvent.Loading -> {

                        binding.progressBar.isVisible = true

                    }
                    else -> Unit
                }

            }
        }



    }

    var isLoading = false
    var isLastPage = false
    var isScrolling = false


    val scrollListener = object : RecyclerView.OnScrollListener(){
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)


            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= QUERY_PAGE_SIZE



            val shouldPaginate = isNotLoadingAndNotLastPage && isAtLastItem  && isTotalMoreThanVisible
                     isScrolling


            Log.d("scrollLog isNotLoad",isNotLoadingAndNotLastPage.toString())
            Log.d("scrollLog isNotLoad",totalItemCount.toString())
            Log.d("scrollLog firstVisible",firstVisibleItemPosition.toString())
            Log.d("scrollLog visibleItem",visibleItemCount.toString())
            Log.d("scrollLog isNotBegin",isNotAtBeginning.toString())
            Log.d("scrollLog isAtLastItem",isAtLastItem.toString())
            Log.d("scrollLog isTotalMore",isTotalMoreThanVisible.toString())
            Log.d("scrollLog isScrolling",isScrolling.toString())


            Log.d("scrollLog pagenumber 1",pagenumber.toString())
            Log.d("scrollLog shouldpagi",shouldPaginate.toString())

            if(shouldPaginate) {

                if (pagenumber <= pageLimit) {
                    viewModel.userList(pagenumber)
                    isScrolling = false
                    pagenumber++
                }


            }


//
//            if (dy > 0){
//                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
//                val visibleItemCount = layoutManager.childCount
//                val pastVisibleItem = layoutManager.findFirstCompletelyVisibleItemPosition()
//                val total = userListAdapter.itemCount
//
//                if (pagenumber <= pageLimit) {
//
//
//                        viewModel.userList(pagenumber)
//
//                }
//            }
        }
    }


    private fun setupRecyclerView() = binding.rvUserList.apply{

        userListAdapter = UserListAdapter(context)
        adapter = userListAdapter
        userListAdapter.notifyDataSetChanged()
        layoutManager = LinearLayoutManager(this@HomeActivity)
        addOnScrollListener(this@HomeActivity.scrollListener)

        Log.d("page","In Setup Recyclerview")

    }






}