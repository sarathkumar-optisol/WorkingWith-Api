package com.example.workingwithapi.main

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.workingwithapi.data.api.modal.Data
import com.example.workingwithapi.data.api.modal.UserDataResponse
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

    sealed class LoginEvent{
        class  Success(val result : String) : LoginEvent()
        class  Failure(val error : String) : LoginEvent()
        object Loading : LoginEvent()
        object Empty : LoginEvent()
    }

    sealed class UserListEvent{
        class  Success(val resultText : Data) : UserListEvent()
        class  Failure(val errorText : String) : UserListEvent()
        object Empty : UserListEvent()

    }



    private val _login = MutableStateFlow<LoginEvent>(LoginEvent.Empty)
    val Login : StateFlow<LoginEvent> = _login

    private val _userList = MutableStateFlow<UserListEvent>(UserListEvent.Empty)
    val UserList : StateFlow<UserListEvent> = _userList





    fun login(
            email : String,
            password : String
    ){

            viewModelScope.launch(dispatchers.io) {
//                    _login.value = LoginEvent.Loading
                when(val loginResponse = repository.getLoginData(email,password)){
                    is Resource.Error -> {
                        _login.value = LoginEvent.Failure("Incorrect")
                        Log.d("MVIEWMODEL","Incorrect")

                    }
                    is Resource.Success -> {
                        val data = loginResponse.data!!.token
                        if (data == null){
                            _login.value = LoginEvent.Failure("UnExpected Error")
                        }else{
                            _login.value = LoginEvent.Success("Logged in")
                            Log.d("MVIEWMODEL","success")
                        }
                    }
                }

            }

    }


    @RequiresApi(Build.VERSION_CODES.N)
    fun userList(){

        viewModelScope.launch(dispatchers.io) {
//                    _login.value = LoginEvent.Loading
            when(val userListResponse = repository.getUserDataList()){


                is Resource.Error -> {
                    Log.d("userList",repository.getUserDataList().toString())
                    _userList.value = UserListEvent.Failure("Failed")
                    Log.d("userList",userListResponse.data.toString())
                    Log.d("userList","Incorrect")
                }
                is Resource.Success -> {
                        Log.d("Response",userListResponse.data.toString())

                    val data = userListResponse.data!!.forEach {
                        _userList.value = UserListEvent.Success(it.data)
                        Log.d("ViewModel",it.data.first_name)
                        Log.d("ViewModel",it.data.last_name)
                        Log.d("ViewModel",it.data.email)
                    }
                    if (data == null){
                        _userList.value = UserListEvent.Failure("error")
                    }else{
                      // _userList.value = UserListEvent.Success()
                        Log.d("Logged","success")
                    }
                }
            }

        }

    }


}