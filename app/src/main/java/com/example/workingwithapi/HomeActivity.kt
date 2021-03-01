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
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.workingwithapi.adapter.UserListAdapter
import com.example.workingwithapi.data.api.modal.Data
import com.example.workingwithapi.data.api.modal.UserListResponse
import com.example.workingwithapi.databinding.ActivityHomeBinding
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


    private lateinit var binding : ActivityHomeBinding
    private val viewModel : MainViewModel by viewModels()

    lateinit var userListAdapter: UserListAdapter

    lateinit var toggle : ActionBarDrawerToggle

    lateinit var sharedPreferences : SharedPreferences

    lateinit var searchList : MutableList<Data>


    var pagenumber = 1
    var pageLimit = 2
    var searchPage = 1


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupToolBar()




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
                            //searchList = event.resultText
                            userListAdapter.userDataResponses = event.resultText

                        Log.d("page", "$pagenumber in sucess")

                            }else {
                            //searchList.addAll(event.resultText)
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


        lifecycleScope.launch {
            viewModel.SearchList.collect{ event ->
                when(event){
                    is MainViewModel.SearchListEvent.Success ->{
                        binding.progressBar.isVisible = false



                        if (event.resultText.size == 0){
                            binding.rvUserList.visibility = View.GONE
                            binding.ivNoData.visibility = View.VISIBLE
                            binding.tvNoData.visibility = View.VISIBLE
                        }else{
                            binding.rvUserList.visibility = View.VISIBLE
                            binding.ivNoData.visibility = View.GONE
                            binding.tvNoData.visibility = View.GONE
                            userListAdapter.userDataResponses = event.resultText
                            searchPage++
                        }


                            Log.d("searchList Home", "${event.resultText} in sucess")
                            Log.d("searchList Home", "${event.resultText.size} in sucess")

                    }
                    is MainViewModel.SearchListEvent.Failure ->{
                        Toast.makeText(this@HomeActivity,"No Network",Toast.LENGTH_SHORT).show()
                        binding.progressBar.isVisible = false
                    }
                    is MainViewModel.SearchListEvent.Loading -> {

                        binding.progressBar.isVisible = true

                    }
                    else -> Unit
                }

            }
        }


    }

    private fun setupToolBar() {

        //setSupportActionBar(binding.topAppBar)


        toggle = ActionBarDrawerToggle(this,binding.drawerLayout,R.string.open,R.string.close)
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

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
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.main,menu)

        val searchItem =  menu?.findItem(R.id.menuSearch)
        if (searchItem!=null){
            val searchView = searchItem.actionView as SearchView
            searchView.queryHint = "Search"
           // val fg = searchView.onActionViewCollapsed()
            searchView.setOnCloseListener(object  : SearchView.OnCloseListener{
                override fun onClose(): Boolean {

                    Log.d("close","Inclose")
                    binding.rvUserList.visibility = View.VISIBLE
                    binding.ivNoData.visibility = View.GONE
                    binding.tvNoData.visibility = View.GONE
                    searchView.onActionViewCollapsed()

                    return true
                }


            })

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(p0: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(p0: String?): Boolean {

                    if (p0!!.isNotEmpty()){
                        val search = p0.toLowerCase()


                        if (pagenumber==1){
                            Log.d("QueryPage","$pagenumber")
                            viewModel.searchList(pagenumber, search)
                        }else{
                            viewModel.searchList(searchPage+1, search)
                        }

                        }else{
                        viewModel.userList(1)
                        pagenumber = 1
                    }
            return true
                }

            })
        }
        return true
    }
//
//    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
//
//        return super.onPrepareOptionsMenu(menu)
//    }

}
