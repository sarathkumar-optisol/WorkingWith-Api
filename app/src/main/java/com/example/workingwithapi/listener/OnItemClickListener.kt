package com.example.workingwithapi.listener

import android.provider.ContactsContract
import com.example.workingwithapi.data.api.modal.Data
import com.example.workingwithapi.data.api.modal.StorageImage

interface OnItemClickListener {

    fun onClick(data : Data)

}