package com.example.workingwithapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.workingwithapi.adapter.ContactListAdapter
import com.example.workingwithapi.data.api.modal.Contact
import com.example.workingwithapi.databinding.ActivityContactListBinding
import com.example.workingwithapi.databinding.ActivityHomeBinding
import java.util.ArrayList

class ContactListActivity : AppCompatActivity() {

    lateinit var binding: ActivityContactListBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactListBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.rvContacts.layoutManager = LinearLayoutManager(this)

        val contactList : MutableList<Contact> = ArrayList()

        val contacts = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,null)
        while (contacts!!.moveToNext()){
            val name = contacts.getString(contacts.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
            val number = contacts.getString(contacts.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))

             val contact = Contact(name, number)

            contactList.add(contact)
        }
        binding.rvContacts.adapter = ContactListAdapter(this,contactList)
        contacts.close()
    }
}