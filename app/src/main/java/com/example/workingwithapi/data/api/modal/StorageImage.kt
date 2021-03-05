package com.example.workingwithapi.data.api.modal

import com.google.gson.annotations.SerializedName

data class StorageImage(
    @SerializedName("imagePath")
     var imagePath: String,
    @SerializedName("imageName")
     var imageName: String
)
