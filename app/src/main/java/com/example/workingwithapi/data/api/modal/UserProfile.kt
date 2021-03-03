package com.example.workingwithapi.data.api.modal

import com.google.gson.annotations.SerializedName

data class UserProfile(
    @SerializedName("data")
    val data: Data,
    @SerializedName("support")
    val support: Support
)