package com.example.workingwithapi.data.api

import com.example.workingwithapi.data.api.modal.LoginRequest
import com.example.workingwithapi.data.api.modal.LoginResponse

import com.example.workingwithapi.data.api.modal.UserListResponse
import retrofit2.Call

import retrofit2.Response
import retrofit2.http.*

interface LoginApi {

    @POST("login")
    suspend fun validateLogin(@Body loginRequest: LoginRequest) :   Response<LoginResponse>

    @GET("users?page{}")
    suspend fun getUserDataList(
            @Query("page") page : Int
    ) : Response<UserListResponse>


}


