package com.example.workingwithapi

import android.provider.ContactsContract
import com.example.workingwithapi.data.api.modal.Data


/**
 * interface to trigger onClick event in a recyclerview
 */
interface OnItemClickListener {

    fun onClick(data : Data)
}