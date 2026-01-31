package com.example.androidcourseshpp.ui.screens.main.contactdetailsnotif

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import com.example.androidcourseshpp.R
import com.example.androidcourseshpp.databinding.FragmentDetailviewNotificationBinding
import com.example.androidcourseshpp.ui.BaseFragment
import com.example.androidcourseshpp.ui.screens.model.ContactDetailsModel
import dagger.hilt.android.AndroidEntryPoint


const val CONTACT_DETAILS = "contactDetails"
const val NOTIFICATION_ACTION = "notificationAction"

@AndroidEntryPoint
class ContactDetailsNotificationFragment :
    BaseFragment<FragmentDetailviewNotificationBinding>(FragmentDetailviewNotificationBinding::inflate) {

    private val viewModel by viewModels<ContactDetailsNotificationViewModel>()

    private val contactDetails =
        requireArguments().getParcelable<ContactDetailsModel>(CONTACT_DETAILS)
    private val notificationAction =
        requireArguments().getParcelable<NotificationAction>(NOTIFICATION_ACTION)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setMainActionButtonText()
        setListeners()
    }

    private fun setMainActionButtonText() = with(binding) {
        notificationAction?.let {
            when (notificationAction) {
                NotificationAction.ADD_CONTACT -> buttonMainAction.text =
                    getString(R.string.add_to_my_contacts)

                NotificationAction.DELETE_CONTACT -> getString(R.string.delete_from_my_contacts)
            }
        }
    }

    override fun setListeners() = with(binding) {
        buttonMainAction.setOnClickListener {
            if (contactDetails != null && notificationAction != null) {
                viewModel.setEvent(
                    ContactDetailsNotificationContract.Event.MainActionButtonClicked(
                        action = notificationAction,
                        contactId = contactDetails.id
                    )
                )
            }
        }

        imageButtonArrowBack.setOnClickListener {
        }
    }


    companion object {
        fun newInstance(
            contactDetails: ContactDetailsModel,
            notificationAction: NotificationAction
        ): ContactDetailsNotificationFragment {

            val args = bundleOf(
                CONTACT_DETAILS to contactDetails,
                NOTIFICATION_ACTION to notificationAction
            )

            val fragment = ContactDetailsNotificationFragment()
            fragment.arguments = args

            return fragment
        }
    }

}