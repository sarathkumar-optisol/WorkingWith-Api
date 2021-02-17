package com.example.workingwithapi.data.api

import com.example.workingwithapi.data.api.modal.LoginResponse
import retrofit2.Response
import retrofit2.http.GET

interface LoginApi {

    @GET("/login")
    //RequestBody
    suspend fun validateLogin() : Response<LoginResponse>
}



