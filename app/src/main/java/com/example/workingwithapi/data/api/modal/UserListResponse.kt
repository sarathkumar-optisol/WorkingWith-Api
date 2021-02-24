package com.example.workingwithapi.data.api.modal

import com.google.gson.annotations.SerializedName

data class UserListResponse(
    @SerializedName("data")
    val data: MutableList<Data>,
    @SerializedName("page")
    val page: Int,
    @SerializedName("per_page")
    val per_page: Int,
    @SerializedName("support")
    val support: Support,
    @SerializedName("total")
    val total: Int,
    @SerializedName("total_pages")
    val total_pages: Int
)