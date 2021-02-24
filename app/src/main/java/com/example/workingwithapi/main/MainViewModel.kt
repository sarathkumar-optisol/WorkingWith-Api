package com.example.workingwithapi.main

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.hilt.lifecycle.ViewModelInject

import androidx.lifecycle.*
import com.example.workingwithapi.data.api.modal.Data


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workingwithapi.data.api.modal.UserListResponse


import com.example.workingwithapi.util.DispatcherProvider
import com.example.workingwithapi.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

const val TAG = "MainViewModel"


class MainViewModel @ViewModelInject constructor(
    private val repository: MainRepository,
    private val dispatchers : DispatcherProvider
) : ViewModel() {



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




    private val _login = MutableStateFlow<LoginEvent>(LoginEvent.Empty)
    val Login: StateFlow<LoginEvent> = _login

    private val _userList = MutableStateFlow<UserListEvent>(UserListEvent.Empty)
    val UserList: StateFlow<UserListEvent> = _userList




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
                        _login.value = LoginEvent.Success("Logged in")
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

                            Log.d("page", "${userlistResponse.data.data} in second api call if")
                        }else{
                            val new = _userList

                            _userList.value = UserListEvent.Success(userlistResponse.data.data)
                            Log.d("page", "${userlistResponse.data.data} in second api call else")

                        }






//                        if (pageNumber != 1) {
//                            repository.getUserDataList(pageNumber)
//                            _userList.value = UserListEvent.Success(userlistResponse.data.data)
//                            val oldData = userlistResponse.data.data
//                            val newData = userlistResponse.data.data
//                            oldData.addAll(newData)
//                        }
//                        Log.d("page",pageNumber.toString())
//                        val oldData = userlistResponse.data.data
//                        repository.getUserDataList(pageNumber)
//                        val newData  = userlistResponse.data.data
//                        Log.d("page",newData.toString())
//
//                        Log.d("page","${oldData.size} Size")
//                        Log.d("page","${newData.size} Size")
//                        oldData.addAll(newData)
//                        Log.d("Logged","success")
                        }

                    }


                }

            }


        }


    }
