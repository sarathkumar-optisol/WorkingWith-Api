package com.example.workingwithapi

import android.provider.ContactsContract
import com.example.workingwithapi.data.api.modal.Data

interface OnItemClickListener {

    fun onClick(data : Data)
}