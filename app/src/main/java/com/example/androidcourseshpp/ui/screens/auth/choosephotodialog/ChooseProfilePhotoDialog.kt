package com.example.androidcourseshpp.ui.screens.auth.choosephotodialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.androidcourseshpp.databinding.DialogChooseProfilePhotoBinding

class ChooseProfilePhotoDialog : DialogFragment(){


    private lateinit var binding: DialogChooseProfilePhotoBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogChooseProfilePhotoBinding.inflate(layoutInflater)

        val dialog = AlertDialog.Builder(requireContext())
            .setView(binding.root)
            .create()

        return dialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    private fun initRecycleView(){

    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.let { window ->
            val layoutParams = window.attributes
            layoutParams.y = MOVEMENT_ALONG_Y
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
            window.attributes = layoutParams
        }
    }
    companion object {
        private const val MOVEMENT_ALONG_Y = 400
    }

}