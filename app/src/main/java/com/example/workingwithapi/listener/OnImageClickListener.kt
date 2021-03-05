package com.example.workingwithapi.listener

import com.example.workingwithapi.data.api.modal.Data
import com.example.workingwithapi.data.api.modal.StorageImage

interface OnImageClickListener {

    fun onClick(image : StorageImage)
}