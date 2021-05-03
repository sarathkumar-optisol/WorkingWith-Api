package com.example.workingwithapi.listener

import com.example.workingwithapi.data.api.modal.JsonResp


interface ServiceListener {

    fun onSuccess(jsonResp: JsonResp, data: String)

    fun onFailure(jsonResp: JsonResp, data: String)
}