package com.example.workingwithapi.listener

import com.example.workingwithapi.data.api.modal.StorageImage
import com.example.workingwithapi.data.api.modal.StorageVideo

interface OnVideoClickListener {

    fun onClick(video : StorageVideo)
}