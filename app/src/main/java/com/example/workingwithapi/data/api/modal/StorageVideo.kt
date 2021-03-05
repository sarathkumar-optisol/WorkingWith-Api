package com.example.workingwithapi.data.api.modal

import com.google.gson.annotations.SerializedName

data class StorageVideo (
    @SerializedName("videoPath")
    var videoPath: String,
    @SerializedName("videoName")
    var videoName: String
    )