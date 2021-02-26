package com.example.workingwithapi.main

import com.example.workingwithapi.data.api.modal.LoginResponse

import com.example.workingwithapi.data.api.modal.UserListResponse
import com.example.workingwithapi.util.Resource

interface MainRepository {

        suspend fun getLoginData(userName : String , password : String) : Resource<LoginResponse>


        suspend fun getUserDataList(pageNumber : Int) : Resource<UserListResponse>

        suspend fun getSearchList(pageNumber: Int , email : String) : Resource<UserListResponse>

}