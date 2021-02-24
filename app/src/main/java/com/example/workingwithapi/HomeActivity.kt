package com.example.workingwithapi

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.workingwithapi.adapter.UserListAdapter
import com.example.workingwithapi.databinding.ActivityHomeBinding
import com.example.workingwithapi.main.MainViewModel
import com.example.workingwithapi.others.Constants.QUERY_PAGE_SIZE
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect



@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {


    private lateinit var binding : ActivityHomeBinding
    private val viewModel : MainViewModel by viewModels()

    lateinit var userListAdapter: UserListAdapter

    lateinit var toggle : ActionBarDrawerToggle

    lateinit var sharedPreferences : SharedPreferences




    var pagenumber = 1
    var pageLimit = 2


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)


        toggle = ActionBarDrawerToggle(this,binding.drawerLayout,R.string.open,R.string.close)
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()


        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        sharedPreferences = getSharedPreferences("token", Context.MODE_PRIVATE)

        var token = sharedPreferences.getString("token", null)
        Log.d("token","${token.toString()}")

        binding.navView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.logOut -> {
                    sharedPreferences.edit().remove("token").commit()
                    Toast.makeText(this,"Logged Out",Toast.LENGTH_SHORT).show()
                    val intent = Intent(this,MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
            true
        }


        binding.progressBar.isVisible = false
        viewModel.userList(pagenumber)
        setupRecyclerView()


        lifecycleScope.launchWhenStarted {
            viewModel.UserList.collect{ event ->
                when(event){
                    is MainViewModel.UserListEvent.Success ->{
                        binding.progressBar.isVisible = false

                        if (pagenumber==1) {
                            userListAdapter.userDataResponses = event.resultText

                        Log.d("page", "$pagenumber in sucess")

                            }else {
                            userListAdapter.differ.submitList(event.resultText)
                        }
                        Log.d("Home",event.toString())
                        Log.d("Home",pagenumber.toString())
                    }
                    is MainViewModel.UserListEvent.Failure ->{
                        Toast.makeText(this@HomeActivity,"No Network",Toast.LENGTH_SHORT).show()
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


    private val scrollListener = object : RecyclerView.OnScrollListener(){
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



            if (dy > 0){
                isScrolling = true
            }
            val shouldPaginate =
                    isNotLoadingAndNotLastPage && isAtLastItem  && isTotalMoreThanVisible && isScrolling


            Log.d("scrollLog isNotLoad",isNotLoadingAndNotLastPage.toString())
            Log.d("scrollLog totalitem",totalItemCount.toString())
            Log.d("scrollLog firstVisible",firstVisibleItemPosition.toString())
            Log.d("scrollLog visibleItem",visibleItemCount.toString())
            Log.d("scrollLog isNotBegin",isNotAtBeginning.toString())
            Log.d("scrollLog isAtLastItem",isAtLastItem.toString())
            Log.d("scrollLog isTotalMore",isTotalMoreThanVisible.toString())
            Log.d("scrollLog isScrolling",isScrolling.toString())


            Log.d("scrollLog pagenumber 1",pagenumber.toString())


            if(shouldPaginate) {

                if (pagenumber <= pageLimit) {
                    viewModel.userList(pagenumber)
                    pagenumber++
                    isScrolling = false

                }


            }

        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)){
            return true
        }

        return super.onOptionsItemSelected(item)
    }


    private fun setupRecyclerView() = binding.rvUserList.apply{

        userListAdapter = UserListAdapter(context)
        adapter = userListAdapter
        userListAdapter.notifyDataSetChanged()
        layoutManager = LinearLayoutManager(this@HomeActivity)
        addOnScrollListener(this@HomeActivity.scrollListener)

        Log.d("page","In Setup Recyclerview")

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.main,menu)
        return true
    }
//
//    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
//
//        return super.onPrepareOptionsMenu(menu)
//    }

}



