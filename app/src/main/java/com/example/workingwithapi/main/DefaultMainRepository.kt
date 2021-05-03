package com.example.workingwith

import com.example.workingwithapi.listener.ServiceListener
import com.example.workingwithapi.main.MainRepository

import android.util.Log
import com.example.workingwithapi.data.api.LoginApi
import com.example.workingwithapi.data.api.modal.*

import com.example.workingwithapi.util.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

class DefaultMainRepository @Inject constructor(
    private val api : LoginApi,
    private val serviceListener: ServiceListener
) : MainRepository {

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



    override suspend fun getUserDataList(pageNumber: Int): Resource<UserListResponse> {
        return try {
            val response = api.getUserDataList(pageNumber)
            Log.d("DefaultViewModel" , response.body().toString())
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

    override suspend fun getSearchList(pageNumber: Int, email: String): Resource<UserListResponse> {



        return try {

            val response = api.getUserDataList(pageNumber)
            Log.d("DefaultViewModel" , response.body().toString())
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

    override suspend fun getUserProfile(): Resource<UserProfile> {
        return try {

            val response = api.getUserProfile()
            Log.d("DefaultViewModel" , response.body().toString())
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

    override suspend fun expUserProfile(): Resource<JsonResp> {
        return try {
            val response = api.expUserProfile()
            val result = response.body()

            if (response.isSuccessful && result != null){
                Resource.Success(result)
            }else{
                Resource.Error(result?.errorMsg!!)
            }
        }
        catch (e : Exception){
            Resource.Error(e.message ?: "An Error Occurred")
        }





    }


}
//JsonResp

//var url: String? = null,
//var method: String? = null,
//var strRequest: String? = null,
//var strResponse: String? = null,
//var responseCode: Int = 0,
//var requestCode: Int = 0,
//var errorMsg: String? = null,
//var requestData: String? = null,
//var isOnline: Boolean = false

