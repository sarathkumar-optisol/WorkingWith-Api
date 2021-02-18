package com.example.workingwithapi.data.api

import com.example.workingwithapi.data.api.modal.LoginRequest
import com.example.workingwithapi.data.api.modal.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface LoginApi {

    @POST("login")
    suspend fun validateLogin(@Body loginRequest: LoginRequest) :   Response<LoginResponse>
}



