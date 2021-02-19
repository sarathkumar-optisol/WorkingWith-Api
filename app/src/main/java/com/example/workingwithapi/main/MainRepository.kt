package com.example.workingwithapi.main

import com.example.workingwithapi.data.api.modal.Data
import com.example.workingwithapi.data.api.modal.LoginResponse
import com.example.workingwithapi.data.api.modal.UserDataResponse
import com.example.workingwithapi.util.Resource

interface MainRepository {

        suspend fun getLoginData(userName : String , password : String) : Resource<LoginResponse>

        suspend fun getUserDataList() : Resource<List<UserDataResponse>>

}