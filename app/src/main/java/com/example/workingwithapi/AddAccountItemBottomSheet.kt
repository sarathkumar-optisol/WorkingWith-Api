package com.example.workingwithapi

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.workingwithapi.databinding.BottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AddAccountItemBottomSheet(context: Context, var addBottomDialogListener: AddBottomDialogueListener) : BottomSheetDialogFragment()  {

    lateinit var binding : BottomSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_sheet,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = BottomSheetBinding.bind(view)



        binding.btnSubmit.setOnClickListener{
            val text = binding.tvNewEntry.text.toString()

            addBottomDialogListener.onAddButtonClicked(text)
            dismiss()
        }
    }


}