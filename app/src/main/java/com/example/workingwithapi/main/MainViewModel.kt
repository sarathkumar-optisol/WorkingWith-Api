package com.example.workingwithapi.main

import android.util.Log
import android.widget.Toast
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workingwithapi.util.DispatcherProvider
import com.example.workingwithapi.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class MainViewModel @ViewModelInject constructor(
    private val repository: MainRepository,
    private val dispatchers : DispatcherProvider
) : ViewModel(){

    sealed class LoginEvent{
        class  Success(val resultText : String) : LoginEvent()
        class  Failure(val errorText : String) : LoginEvent()
        object Loading : LoginEvent()
        object Empty : LoginEvent()
    }

    private val _login = MutableStateFlow<LoginEvent>(LoginEvent.Empty)
    val Login : StateFlow<LoginEvent> = _login

    fun login(
            email : String,
            password : String
    ){
//            if (email.isEmpty() && password.isEmpty()){
//                _login.value = LoginEvent.Failure("Not a Valid Email or Password")
//            }

            viewModelScope.launch(dispatchers.io) {
//                    _login.value = LoginEvent.Loading
                when(val loginResponse = repository.getLoginData(email,password)){
                    is Resource.Error -> {
                        _login.value = LoginEvent.Failure("Incorrect")
                        Log.d("Logged","Incorrect")
                    }
                    is Resource.Success -> {
                        val data = loginResponse.data!!.token
                        if (data == null){
                            _login.value = LoginEvent.Failure("UnExpected Error")
                        }else{
                            _login.value = LoginEvent.Success("Logged in")
                            Log.d("Logged","success")
                        }
                    }
                }

            }

    }
}