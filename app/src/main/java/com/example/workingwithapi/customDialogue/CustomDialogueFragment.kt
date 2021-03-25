package com.example.workingwithapi.customDialogue

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.workingwithapi.R
import com.example.workingwithapi.databinding.ActivityCustomDialogueBinding
import com.example.workingwithapi.databinding.FragmentProfileBinding

/**
 * custom dialogue
 */
class CustomDialogueFragment  : DialogFragment() {

    lateinit var binding: ActivityCustomDialogueBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

    var view = inflater.inflate(R.layout.activity_custom_dialogue,container)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = ActivityCustomDialogueBinding.bind(view)

        //dialog!!.window!!.setLayout(350,350)


        binding.btnCancel.setOnClickListener {
            dismiss()
        }

        binding.btnSubmit.setOnClickListener {
            val selectedId = binding.radioGroup.checkedRadioButtonId

            val radio = view.findViewById<RadioButton>(selectedId)

            val number = binding.etNumber.text

            val role = radio.text.toString()
            Toast.makeText(context, "Selected $role and number is $number", Toast.LENGTH_SHORT).show()

            dismiss()

        }
    }
}