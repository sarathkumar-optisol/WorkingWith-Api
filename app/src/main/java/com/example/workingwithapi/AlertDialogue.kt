package com.example.workingwithapi

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.workingwithapi.databinding.ActivityAlertDialogueBinding
import com.example.workingwithapi.databinding.ActivityHomeBinding

class AlertDialogue : AppCompatActivity() {

    private lateinit var binding: ActivityAlertDialogueBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlertDialogueBinding.inflate(layoutInflater)
        setContentView(binding.root)



        val addContactDialog = AlertDialog.Builder(this)
            .setTitle("Add Contact")
            .setMessage("Do you want to add?")
            .setIcon(R.drawable.ic_baseline_person_add_24)
            .setPositiveButton("Yes"){ _,_: Int ->
                Toast.makeText(this,"Contact Added",Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("No"){_,_ : Int->
                Toast.makeText(this,"Contact not Added",Toast.LENGTH_SHORT).show()
            }
            .create()
        binding.btnDialog1.setOnClickListener {
            addContactDialog.show()
        }

        val options = arrayOf("First Option","Second Option","Third Option")
        val singleOptionDialog = AlertDialog.Builder(this)
            .setTitle("Choose any of these")
            .setSingleChoiceItems(options,0){ dialogInterface: DialogInterface, i: Int ->
                Toast.makeText(this,"You Clicked ${options[i]}",Toast.LENGTH_SHORT).show()
            }
            .setPositiveButton("Accept") { _: DialogInterface, _: Int ->
                Toast.makeText(this,"Your Choice Accepted",Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Decline"){ dialogInterface: DialogInterface, i: Int ->
                Toast.makeText(this,"You Choice Declined",Toast.LENGTH_SHORT).show()
            }
            .create()

        binding.btnDialog2.setOnClickListener {
            singleOptionDialog.show()
        }


        val multiChoiceDialog = AlertDialog.Builder(this)
            .setTitle("Choose any of these")
            .setMultiChoiceItems(options, booleanArrayOf(false,false,false)){ _: DialogInterface, i: Int, isChecked: Boolean ->
                if (isChecked){
                    Toast.makeText(this,"You Checked ${options[i]}",Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this,"You UnChecked ${options[i]}",Toast.LENGTH_SHORT).show()

                }

            }
            .setPositiveButton("Accept") { _: DialogInterface, _: Int ->
                Toast.makeText(this,"Your MultiChoice Accepted",Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Decline"){ dialogInterface: DialogInterface, i: Int ->
                Toast.makeText(this,"You MultiChoice Declined",Toast.LENGTH_SHORT).show()
            }
            .create()

        binding.btnDialog3.setOnClickListener {
            multiChoiceDialog.show()
        }
    }
}