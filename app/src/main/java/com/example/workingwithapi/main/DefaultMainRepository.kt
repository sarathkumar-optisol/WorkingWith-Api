package com.example.workingwithapi.main

import com.example.workingwithapi.data.api.LoginApi
import com.example.workingwithapi.data.api.modal.LoginRequest
import com.example.workingwithapi.data.api.modal.LoginResponse
import com.example.workingwithapi.util.Resource
import java.lang.Exception
import javax.inject.Inject

class DefaultMainRepository @Inject constructor(
    private val api : LoginApi
) : MainRepository{

    override suspend fun getLoginData(userName: String, password: String): Resource<LoginResponse> {
        return try {
            val response = api.validateLogin(LoginRequest(userName,password))
            val result = response.body()

            if (response.isSuccessful && result != null){
                Resource.Success(result)
            }else{
                Resource.Error(response.message())
            }
        }catch (e : Exception){
            Resource.Error(e.message ?: "An Error Occurred")
        }
    }


}