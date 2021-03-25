package com.example.workingwithapi.data.api

import com.example.workingwithapi.data.api.modal.*

import retrofit2.Call

import retrofit2.Response
import retrofit2.http.*


/**
 * making api calls
 * having endpoints
 */
interface LoginApi {

    @POST("login")
    suspend fun validateLogin(@Body loginRequest: LoginRequest) :   Response<LoginResponse>

    @GET("users?page{}")
    suspend fun getUserDataList(
            @Query("page") page : Int
    ) : Response<UserListResponse>

    @GET("users/2")
    suspend fun getUserProfile() : Response<UserProfile>


}


