package com.example.androidcourseshpp.ui.screens.contacts

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import com.example.androidcourseshpp.R
import com.example.androidcourseshpp.databinding.DialogAddContactBinding

class AddContactDialog : DialogFragment() {
    private lateinit var binding: DialogAddContactBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogAddContactBinding.inflate(layoutInflater)

        val listener = DialogInterface.OnClickListener { _, which ->
            parentFragmentManager.setFragmentResult(
                REQUEST_KEY, bundleOf(
                    RESPONSE_KEY to which,
                    NAME_KEY to binding.etContactName.text.toString(),
                    CAREER_KEY to binding.etContactCareer.text.toString()
                )
            )
            /* parentFragmentManager.setFragmentResult(
                 CONTACT_DATA, bundleOf(
                     NAME_KEY to binding.etContactName.text.toString(),
                     CAREER_KEY to binding.etContactCareer.text.toString()
                 )
             )*/

        }

        val dialog = AlertDialog.Builder(requireContext())
            .setIcon(R.drawable.ic_dialog_add_contact)
            .setTitle("Add new contact")
            .setMessage("Enter the data of the new contact")
            .setView(binding.root)
            .setPositiveButton("Save", listener)
            .setNegativeButton("Cancel", listener)
            .create()

        return dialog
    }

    companion object {
        val TAG = AddContactDialog::class.java.simpleName
        val REQUEST_KEY = "requestKey - $TAG"
        const val NAME_KEY = "contactName"
        const val CAREER_KEY = "contactCareer"
        const val CONTACT_DATA = "contactData"
        const val RESPONSE_KEY = "pressedButton"
        const val NEW_CONTACT_AVATAR =
            "https://kartinki.pics/uploads/posts/2022-02/1645235615_4-kartinkin-net-p-kroliki-kartinki-4.jpg"
    }

}