package com.example.androidcourseshpp.ui.screens.main.contactdetailsnotif

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.androidcourseshpp.databinding.FragmentDetailviewBinding
import com.example.androidcourseshpp.ui.BaseFragment
import com.example.androidcourseshpp.ui.notifications.NotificationAction
import com.example.androidcourseshpp.ui.screens.model.ContactDetailsModel
import com.example.androidcourseshpp.ui.utils.loadImageFromURLCircled
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContactDetailsNotificationFragment :
    BaseFragment<FragmentDetailviewBinding>(FragmentDetailviewBinding::inflate) {

    private val viewModel by viewModels<ContactDetailsNotificationViewModel>()

    private val args: ContactDetailsNotificationFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        renderUI()
        setUserInfo()
        setObservers()
        setListeners()
    }

    private fun setUserInfo() = with(binding) {
        args.apply {
            textViewName.text = name
            textViewCareer.text = career
            textViewHomeAddress.text = address
            circleViewProfilePhoto.loadImageFromURLCircled(requireContext(), avatarURL)
        }
    }

    private fun renderUI() = with(binding) {
        buttonOutlineMessage.visibility = View.VISIBLE
        val notificationAction = NotificationAction.entries[args.notifId]
        when (notificationAction) {
            NotificationAction.ADD_CONTACT -> buttonDeleteFromMyContacts.visibility =
                View.VISIBLE

            NotificationAction.DELETE_CONTACT -> buttonAddToMyContacts.visibility = View.VISIBLE
        }
    }

    private fun setObservers() = with(binding) {
        var toast: Toast? = null
        collectFlowWithLifecycle(viewModel.effect) { effect ->
            when (effect) {
                is ContactDetailsNotificationContract.Effect.NavigateToPreviousScreen -> requireActivity().onNavigateUp()
                is ContactDetailsNotificationContract.Effect.ShowToast -> {
                    toast?.cancel()
                    toast = makeToast(effect.message)
                    toast.show()
                }

                is ContactDetailsNotificationContract.Effect.MainActionWasExecuted -> {
                    buttonAddToMyContacts.visibility = View.GONE
                    buttonDeleteFromMyContacts.visibility = View.GONE
                    buttonOutlineMessage.visibility = View.GONE
                    buttonFilledMessage.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun setListeners() = with(binding) {
        val contactDetails = with(args) {
            ContactDetailsModel(
                id = id,
                name = name,
                career = career,
                avatarURL = avatarURL,
                address = address,
            )
        }
        buttonAddToMyContacts.setOnClickListener {
            viewModel.setEvent(
                ContactDetailsNotificationContract.Event.OnAddContactButtonClicked(contactDetails)
            )
        }
        buttonDeleteFromMyContacts.setOnClickListener {
            viewModel.setEvent(
                ContactDetailsNotificationContract.Event.OnDeleteContactButtonClicked(contactDetails)
            )
        }
        imageButtonArrowBack.setOnClickListener {
            viewModel.setEvent(ContactDetailsNotificationContract.Event.OnArrowBackButtonClicked)
        }
    }
}