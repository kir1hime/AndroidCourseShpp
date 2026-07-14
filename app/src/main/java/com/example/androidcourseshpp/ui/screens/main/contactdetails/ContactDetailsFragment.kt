package com.example.androidcourseshpp.ui.screens.main.contactdetails

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.example.androidcourseshpp.R
import com.example.androidcourseshpp.databinding.FragmentDetailviewBinding
import com.example.androidcourseshpp.ui.BaseFragment
import com.example.androidcourseshpp.ui.utils.loadImageFromURLCircled
import dagger.hilt.android.AndroidEntryPoint

const val REQUEST_CODE = "requestCode"
const val TO_RELOAD_USER_LIST = "toReloadList"

@AndroidEntryPoint
class ContactDetailsFragment :
    BaseFragment<FragmentDetailviewBinding>(FragmentDetailviewBinding::inflate) {

    private val args: ContactDetailsFragmentArgs by navArgs()
    private val viewModel by viewModels<ContactDetailsViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        renderUI(args.toShowAddContactButton)
        setObservers()
        profilePhotoTransition()
        setContactDetailsInfo()
        setListeners()
    }

    private fun setObservers() {
        collectFlowWithLifecycle(viewModel.effect) { effect ->
            when (effect) {
                is ContactDetailsContract.Effect.ShowToast -> {
                    val toast = makeToast(effect.toastMessageResId)
                    toast.show()
                }

                is ContactDetailsContract.Effect.ContactWasAdded -> {
                    parentFragmentManager.setFragmentResult(
                        REQUEST_CODE,
                        bundleOf(TO_RELOAD_USER_LIST to args.contactDetails.id)
                    )
                }
            }
        }
    }

    private fun renderUI(isAddToMyContactsButtonVisible: Boolean) = with(binding) {
        if (isAddToMyContactsButtonVisible) {
            buttonAddToMyContacts.visibility = View.VISIBLE
            buttonOutlineMessage.visibility = View.VISIBLE
            buttonFilledMessage.visibility = View.INVISIBLE
        } else {
            buttonAddToMyContacts.visibility = View.INVISIBLE
            buttonOutlineMessage.visibility = View.INVISIBLE
            buttonFilledMessage.visibility = View.VISIBLE
        }
    }

    private fun profilePhotoTransition() {
        sharedElementEnterTransition = TransitionInflater.from(requireContext())
            .inflateTransition(R.transition.shared_element_transition)
        binding.circleViewProfilePhoto.transitionName = args.contactDetails.id.toString()

    }

    private fun setContactDetailsInfo() = with(binding) {
        val avatarURL = args.contactDetails.avatarURL

        if (avatarURL.isBlank()) {
            circleViewProfilePhoto.setImageResource(R.drawable.profile_mockup)
        } else {
            circleViewProfilePhoto.loadImageFromURLCircled(
                requireContext(),
                avatarURL
            )
        }

        args.contactDetails.apply {
            textViewName.text = name
            textViewCareer.text = career
            textViewHomeAddress.text = address
        }
    }

    private fun setListeners() = with(binding) {
        imageButtonArrowBack.setOnClickListener {
            findNavController().navigateUp()
        }
        buttonAddToMyContacts.setOnClickListener {
            renderUI(isAddToMyContactsButtonVisible = false)
            viewModel.setEvent(ContactDetailsContract.Event.OnAddContactButtonClicked(args.contactDetails))
        }
    }
}