package com.example.androidcourseshpp.ui.screens.contacts

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.androidcourseshpp.R
import com.example.androidcourseshpp.databinding.DialogAddContactBinding

class AddContactDialog : DialogFragment() {
    private lateinit var binding: DialogAddContactBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogAddContactBinding.inflate(layoutInflater)

        val listener = DialogInterface.OnClickListener{ _, which ->
        }

        val dialog =  AlertDialog.Builder(requireContext())
            .setIcon(R.drawable.ic_dialog_add_contact)
            .setTitle("Add new contact")
            .setMessage("Enter the name and career of the new contact")
            .setView(binding.root)
            .setPositiveButton("Save", listener)
            .setNegativeButton("Cancel", listener)
            .create()

        return dialog
    }

}