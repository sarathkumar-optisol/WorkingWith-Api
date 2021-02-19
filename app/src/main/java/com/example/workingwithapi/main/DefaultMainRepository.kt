package com.example.workingwithapi.main

import android.util.Log
import com.example.workingwithapi.data.api.LoginApi
import com.example.workingwithapi.data.api.modal.Data
import com.example.workingwithapi.data.api.modal.LoginRequest
import com.example.workingwithapi.data.api.modal.LoginResponse
import com.example.workingwithapi.data.api.modal.UserDataResponse
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

    override suspend fun getUserDataList(): Resource<List<UserDataResponse>> {
        return try {
            val response = api.getUsers()
            val result = response.body()
            Log.d("apicall",response.body().toString())
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


