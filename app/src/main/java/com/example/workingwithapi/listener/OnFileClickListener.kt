package com.example.workingwithapi.listener

import com.example.workingwithapi.data.api.modal.StorageFile
import com.example.workingwithapi.data.api.modal.StorageImage

interface OnFileClickListener {

    fun onClick(file: StorageFile)
}