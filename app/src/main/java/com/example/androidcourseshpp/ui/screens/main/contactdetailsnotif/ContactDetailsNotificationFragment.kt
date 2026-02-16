package com.example.androidcourseshpp.ui.screens.main.contactdetailsnotif

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.androidcourseshpp.R
import com.example.androidcourseshpp.databinding.FragmentDetailviewNotificationBinding
import com.example.androidcourseshpp.ui.BaseFragment
import com.example.androidcourseshpp.ui.notifications.NotificationAction
import com.example.androidcourseshpp.ui.utils.loadImageFromURLCircled
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContactDetailsNotificationFragment :
    BaseFragment<FragmentDetailviewNotificationBinding>(FragmentDetailviewNotificationBinding::inflate) {

    private val viewModel by viewModels<ContactDetailsNotificationViewModel>()

    private val args: ContactDetailsNotificationFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setMainActionButtonText()
        setUserInfo()
        setObservers()
        setListeners()
    }

    private fun setUserInfo() = with(binding) {
        args.apply {
            textViewName.text = name
            textViewCareer.text = career
            textViewHomeAddress.text = address
            circleImageViewProfilePhoto.loadImageFromURLCircled(requireContext(), avatarURL)
        }
    }

    private fun setMainActionButtonText() = with(binding) {
        val notificationAction = NotificationAction.entries[args.notifId]
        when (notificationAction) {
            NotificationAction.ADD_CONTACT -> buttonMainAction.text =
                getString(R.string.add_to_my_contacts)

            NotificationAction.DELETE_CONTACT -> buttonMainAction.text =
                getString(R.string.delete_from_my_contacts)
        }
    }

    override fun setObservers()  {
        collectFlow(viewModel.effect) { effect ->
            when (effect) {
                is ContactDetailsNotificationContract.Effect.NavigateToPreviousScreen -> requireActivity().onNavigateUp()
                is ContactDetailsNotificationContract.Effect.ShowToast -> Toast.makeText(
                    requireContext(), getString(effect.message),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }


    override fun setListeners() = with(binding) {
        imageButtonArrowBack.setOnClickListener {
            viewModel.setEvent(ContactDetailsNotificationContract.Event.OnArrowBackButtonClicked)
        }
    }


}