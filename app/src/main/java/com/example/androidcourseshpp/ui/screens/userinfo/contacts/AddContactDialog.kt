package com.example.androidcourseshpp.ui.screens.userinfo.contacts

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
                    NAME_KEY to binding.editTextContactName.text.toString(),
                    CAREER_KEY to binding.editTextContactCareer.text.toString()
                )
            )
        }

        val dialog = AlertDialog.Builder(requireContext())
            .setIcon(R.drawable.ic_dialog_add_contact)
            .setTitle(R.string.add_contact_dialog_title)
            .setMessage(R.string.add_contact_dialog_message)
            .setView(binding.root)
            .setPositiveButton(R.string.add_contact_dialog_positive_button_text, listener)
            .setNegativeButton(R.string.add_contact_dialog_negative_button_text, listener)
            .create()

        return dialog
    }

    companion object {
        val TAG = AddContactDialog::class.java.simpleName
        val REQUEST_KEY = "requestKey - $TAG"
        const val NAME_KEY = "contactName"
        const val CAREER_KEY = "contactCareer"
        const val RESPONSE_KEY = "pressedButton"
    }

}