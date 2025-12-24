package com.example.androidcourseshpp.ui.screens.main.chooseprofilephotodialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.ViewGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.androidcourseshpp.R
import com.example.androidcourseshpp.databinding.DialogChooseProfilePhotoBinding
import com.example.androidcourseshpp.ui.screens.main.chooseprofilephotodialog.adapter.GalleryAdapter
import com.example.androidcourseshpp.ui.screens.main.chooseprofilephotodialog.adapter.GalleryItemDecoration
import com.example.androidcourseshpp.ui.screens.main.chooseprofilephotodialog.adapter.GalleryItemActions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ChooseProfilePhotoDialog : DialogFragment() {
    private lateinit var binding: DialogChooseProfilePhotoBinding
    private val viewModel by viewModels<ChooseProfilePhotoViewModel>()

    private val pickMedia =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                parentFragmentManager.setFragmentResult(
                    REQUEST_KEY,
                    bundleOf(PHOTO to uri.toString())
                )
                findNavController().navigateUp()
            } else {
                findNavController().navigateUp()
            }
        }
    private val adapter: GalleryAdapter by lazy {
        GalleryAdapter(object : GalleryItemActions {

            override fun choosePhoto(photo: String) {
                parentFragmentManager.setFragmentResult(REQUEST_KEY, bundleOf(PHOTO to photo))
                findNavController().navigateUp()
            }
        })
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogChooseProfilePhotoBinding.inflate(layoutInflater)

        initRecycleView()
        setObservers()
        setListeners()
        binding.textViewOpenGallery.setOnClickListener { launchMediaPicker() }

        val dialog = AlertDialog.Builder(requireContext())
            .setView(binding.root)
            .create()

        return dialog
    }

    private fun launchMediaPicker() {
        pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private fun setListeners() = with(binding) {
        textViewCancel.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.galleryPhotos.collect { photos ->
                    adapter.submitList(photos)
                }
            }
        }
    }

    private fun initRecycleView() = with(binding.recyclerViewGallery) {
        adapter = this@ChooseProfilePhotoDialog.adapter

        addItemDecoration(
            GalleryItemDecoration(
                resources.getDimensionPixelSize(R.dimen.gallery_recycler_view_left_offset)
            )
        )

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
        const val REQUEST_KEY = "REQUEST_KEY"
        const val PHOTO = "PHOTO"
    }

}