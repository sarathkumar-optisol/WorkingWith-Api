package com.example.workingwithapi.data.api.modal

import com.google.gson.annotations.SerializedName

class StorageFile(
        @SerializedName("filePath")
        var filePath: String,
        @SerializedName("fileName")
        var fileName: String
)