package com.example.workingwithapi.main

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.util.Log
import android.view.SearchEvent
import androidx.annotation.RequiresApi
import androidx.hilt.lifecycle.ViewModelInject

import androidx.lifecycle.*
import com.example.workingwithapi.data.api.modal.Data


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workingwithapi.HomeFragment
import com.example.workingwithapi.data.api.modal.UserListResponse


import com.example.workingwithapi.util.DispatcherProvider
import com.example.workingwithapi.util.Resource
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.reflect.KProperty

const val TAG = "MainViewModel"


class MainViewModel @ViewModelInject constructor(
    private val repository: MainRepository,
    private val dispatchers : DispatcherProvider
) : ViewModel() {

    lateinit var newList : MutableList<Data>
    lateinit var oldList : MutableList<Data>
    lateinit var search : MutableList<Data>

    sealed class LoginEvent {
        class Success(val result: String) : LoginEvent()
        class Failure(val error: String) : LoginEvent()
        object Loading : LoginEvent()
        object Empty : LoginEvent()
    }

    sealed class UserListEvent {
        class Success(val resultText: MutableList<Data>) : UserListEvent()
        class Failure(val errorText: String) : UserListEvent()
        object Loading : UserListEvent()
        object Empty : UserListEvent()
    }

    sealed class SearchListEvent {
        class Success(val resultText: MutableList<Data>) : SearchListEvent()
        class Failure(val errorText: String) : SearchListEvent()
        object Loading : SearchListEvent()
        object Empty : SearchListEvent()
    }

    sealed class UserProfileEvent {
        class Success(val resultText: Data) : UserProfileEvent()
        class Failure(val errorText: String) : UserProfileEvent()
        object Loading : UserProfileEvent()
        object Empty : UserProfileEvent()
    }






    private val _login = MutableStateFlow<LoginEvent>(LoginEvent.Empty)
    val Login: StateFlow<LoginEvent> = _login

    private val _userList = MutableStateFlow<UserListEvent>(UserListEvent.Empty)
    val UserList: StateFlow<UserListEvent> = _userList

    private val _searchList = MutableStateFlow<SearchListEvent>(SearchListEvent.Empty)
    val SearchList: StateFlow<SearchListEvent> = _searchList


    private val _userProfile = MutableStateFlow<UserProfileEvent>(UserProfileEvent.Empty)
    val UserProfile: StateFlow<UserProfileEvent> = _userProfile




    fun login(
            email: String,
            password: String
    ) {

        viewModelScope.launch(dispatchers.io) {
            _login.value = LoginEvent.Loading
            when (val loginResponse = repository.getLoginData(email, password)) {
                is Resource.Error -> {
                    _login.value = LoginEvent.Failure("Incorrect")
                    Log.d("MVIEWMODEL", "Incorrect")

                }
                is Resource.Success -> {
                    val data = loginResponse.data!!.token
                    if (data == null) {
                        _login.value = LoginEvent.Failure("UnExpected Error")
                    } else {
                        _login.value = LoginEvent.Success(data)
                        Log.d("MVIEWMODEL", "success")
                    }
                }
            }

        }


    }

    fun userList(pageNumber : Int) {

        viewModelScope.launch(dispatchers.io) {
            _userList.value = UserListEvent.Loading
            when (val userlistResponse = repository.getUserDataList(pageNumber)) {
                is Resource.Error -> {
                    _userList.value = UserListEvent.Failure("Incorrect")
                    Log.d("Logged", "Incorrect")
                }
                is Resource.Success -> {
                    val data = userlistResponse.data!!.data
                    if (data == null) {
                        _userList.value = UserListEvent.Failure("UnExpected Error")
                    } else {

                        if (pageNumber==1) {
                            _userList.value = UserListEvent.Success(userlistResponse.data.data)
                            newList = userlistResponse.data.data


                            Log.d("page", "${userlistResponse.data.data} in second api call if")
                        }else{
                            oldList = userlistResponse.data.data
                            oldList.addAll(0,newList)

                            _userList.value = UserListEvent.Success(oldList)
                            Log.d("page", "${userlistResponse.data.data} in second api call else")

                        }
                        }

                    }

                }

            }

        }


    fun searchList(searchPage: Int ,
            email: String
    ) {

        viewModelScope.launch(dispatchers.io) {
            _searchList.value = SearchListEvent.Loading
            when (val searchResponse = repository.getSearchList(searchPage,email)) {
                is Resource.Error -> {
                    _searchList.value = SearchListEvent.Failure("Incorrect")
                    Log.d("MVIEWMODEL", "Incorrect")

                }
                is Resource.Success -> {
                    val data = searchResponse.data!!.data

                    if (data == null) {
                        _searchList.value = SearchListEvent.Failure("UnExpected Error")
                    } else {

                        if (searchPage == 1){
                            Log.d("searchpage","$searchPage")
                            search  = data.filter {
                                it.email.contains(email)
                            } as MutableList<Data>
                            Log.d("","$searchPage")

                        }else{

                            search  = oldList.filter {
                                it.email.contains(email)
                            } as MutableList<Data>
                            Log.d("inelsedata","$search")


                            // code to search email on pagenumber not equal to


                        }

                        _searchList.value = SearchListEvent.Success(search)
                        Log.d("MVIEWMODEL","$search")
                    }
                }
            }

        }


    }

    fun UserProfile() {

        viewModelScope.launch(dispatchers.io) {
            _userProfile.value = UserProfileEvent.Loading
            when (val userProfileResponse = repository.getUserProfile()) {
                is Resource.Error -> {
                    _userProfile.value = UserProfileEvent.Failure("Incorrect")
                    Log.d("MVIEWMODEL", "Incorrect")

                }
                is Resource.Success -> {
                    val data  = userProfileResponse.data!!.data
                    if (data == null) {
                        _userProfile.value = UserProfileEvent.Failure("UnExpected Error")
                    } else {
                        _userProfile.value = UserProfileEvent.Success(data)
                        Log.d("MVIEWMODEL", "success")
                    }
                }
            }

        }


    }

    fun getSharePrefData(){

    }


}


