package com.example.androidcourseshpp.ui.screens.main.editprofile

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.androidcourseshpp.R
import com.example.androidcourseshpp.data.network.entity.user.UpdateUserData
import com.example.androidcourseshpp.databinding.FragmentEditProfileBinding
import com.example.androidcourseshpp.ui.BaseFragment
import com.example.androidcourseshpp.ui.utils.loadImageFromURLCircled
import com.example.androidcourseshpp.ui.utils.onChangeTextListener
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class EditProfileFragment : BaseFragment<FragmentEditProfileBinding>(FragmentEditProfileBinding::inflate) {
    private val viewModel by viewModels<EditProfileViewModel>()
    private val args: EditProfileFragmentArgs by navArgs()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)
        setUserInfo()
        setListeners()
        setObservers()
        formatMobilePhoneInput(editTextMobilePhone)
        setChooseProfilePhotoDialogResultListener(
            circleImageViewProfilePhoto
        ) { photoUrl ->
            viewModel.setEvent(EditProfileContract.Event.ProfilePhotoUpdated(photoUrl))
        }
    }

    private fun setUserInfo() = with(binding) {
        viewModel.setEvent(EditProfileContract.Event.SetUserInfo(args.userServerId))
    }


    @SuppressLint("ClickableViewAccessibility")
    override fun setListeners() = with(binding) {

        buttonSave.setOnClickListener {
            onSaveButtonClick()
        }

        imageButtonAddProfilePhoto.setOnClickListener { onAddProfilePhotoButtonClick() }

        editTextDateOfBirthday.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                setDate(editTextDateOfBirthday::setText)
                true
            } else {
                false
            }
        }

        setEditTextFocusChangedListener(editTextUsername) {
            EditProfileContract.Event.UserNameUpdated(
                getTextFromEditText(editTextUsername)
            )
        }

        setEditTextFocusChangedListener(editTextCareer) {
            EditProfileContract.Event.CareerUpdated(
                getTextFromEditText(editTextCareer)
            )
        }

        setEditTextFocusChangedListener(editTextAddress) {
            EditProfileContract.Event.AddressUpdated(
                getTextFromEditText(editTextAddress)
            )
        }
        setEditTextFocusChangedListener(editTextMobilePhone) {
            EditProfileContract.Event.MobilePhoneUpdated(
                getTextFromEditText(editTextMobilePhone)
            )
        }

        editTextDateOfBirthday.onChangeTextListener { _, _, _, _ ->
            viewModel.setEvent(
                EditProfileContract.Event.DateOfBirthdayUpdated(
                    reversDateFormatting(
                        getTextFromEditText(editTextDateOfBirthday)
                    )
                )
            )
        }
    }


    private fun setEditTextFocusChangedListener(
        editText: EditText,
        event: (String) -> EditProfileContract.Event,
    ) {
        editText.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                viewModel.setEvent(
                    event(getTextFromEditText(editText))
                )
            }
        }
    }

    private fun onSaveButtonClick() = with(binding) {
        defocusAllEditTexts()
        viewModel.setEvent(
            EditProfileContract.Event.OnSaveButtonClicked(
                userServerId = args.userServerId,
                updateUserData =
                    UpdateUserData(
                        name = getTextFromEditText(editTextUsername),
                        career = getTextFromEditText(editTextCareer),
                        address = getTextFromEditText(editTextAddress),
                        phone = getTextFromEditText(editTextMobilePhone),
                        birthday =  reversDateFormatting(
                            getTextFromEditText(editTextDateOfBirthday)
                        )
                    )
            )
        )
    }

    private fun onAddProfilePhotoButtonClick() {
        defocusAllEditTexts()
        viewModel.setEvent(EditProfileContract.Event.OnAddProfilePhotoImageViewClicked)
    }

    override fun setObservers() = with(binding) {
        collectFlow(viewModel.state) { state ->

            editTextUsername.setText(state.userName)
            editTextCareer.setText(state.career)
            editTextAddress.setText(state.address)
            editTextMobilePhone.setText(state.mobilePhone)

            state.dateOfBirthday?.let { date ->
                editTextDateOfBirthday.setText(dateFormating(date))
            }

            circleImageViewProfilePhoto.loadImageFromURLCircled(
                requireContext(),
                state.avatar,
                R.drawable.avatar
            )
            buttonSave.isEnabled = state.isSaveButtonEnabled
            progressBarRequest.isVisible = state.isProgressBarShowed
            setLoadingState(state.isProgressBarShowed, binding)
        }

        collectFlow(viewModel.effect) { effect ->
            when (effect) {
                is EditProfileContract.Effect.NavigateToChooseProfilePhotoDialog -> moveToChooseProfilePhotoDialog()
                is EditProfileContract.Effect.NavigateToUserProfileScreen -> {
                    moveBackToUserProfileScreen()
                }

                is EditProfileContract.Effect.ShowToast -> makeToast(effect.toastMessageResId)
            }
        }
    }

    fun moveBackToUserProfileScreen() {
        findNavController().navigateUp()
    }

    private fun moveToChooseProfilePhotoDialog() {
        val direction =
            EditProfileFragmentDirections.actionEditProfileFragmentToChooseProfilePhotoDialog()
        findNavController().navigate(direction)
    }

    private fun dateFormating(date: Date): String {
        val simpleDateFormat = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
        return simpleDateFormat.format(date)
    }

    private fun reversDateFormatting(date: String): Date? {
        if(date.isBlank() || date.isEmpty()){
            return null
        }
        val simpleDateFormat = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
        return simpleDateFormat.parse(date)
    }

    private fun defocusAllEditTexts() = with(binding) {
        editTextUsername.isFocusable = false
        editTextCareer.isFocusable = false
        editTextAddress.isFocusable = false
        editTextMobilePhone.isFocusable = false
        editTextDateOfBirthday.isFocusable = false
    }


    private fun setDate(dateHolder: (String) -> Unit) {
        val calendar = Calendar.getInstance()

        val datePicker = DatePickerDialog.OnDateSetListener { _, year, month, day ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, day)
            dateHolder(dateFormating(calendar.time))
        }

        DatePickerDialog(
            requireContext(),
            datePicker,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    companion object {
        const val DATE_FORMAT = "dd/MM/yyyy"
    }
}