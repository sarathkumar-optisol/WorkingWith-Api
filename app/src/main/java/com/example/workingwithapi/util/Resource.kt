package com.example.workingwithapi.util


/**
 * used to handle the response SUCCESS OR FAILURE
 */
sealed class Resource<T>(val data : T? , val message : String?) {

    class Success<T>(data: T?) :Resource<T>(data,null)
    class Error<T>(message : String) :Resource<T>(null, message)


}