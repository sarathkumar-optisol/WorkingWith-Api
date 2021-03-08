package com.example.workingwithapi.data.api.modal

import com.google.gson.annotations.SerializedName

data class Contact(
    @SerializedName("name")
    val name : String,
    @SerializedName("number")
    val number : String
)