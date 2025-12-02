package com.example.androidcourseshpp.ui.screens.editprofile

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.androidcourseshpp.R
import com.example.androidcourseshpp.data.network.service.user.entity.UpdateUserData
import com.example.androidcourseshpp.databinding.FragmentEditProfileBinding
import com.example.androidcourseshpp.ui.BaseFragment
import com.example.androidcourseshpp.ui.extensions.loadImageFromURLCircled
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class EditProfileFragment : BaseFragment() {
    private lateinit var binding: FragmentEditProfileBinding
    private val viewModel by viewModels<EditProfileViewModel>()
    private val args: EditProfileFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)
        setUserInfo()
        setListeners()
        setObservers()
        setChooseProfilePhotoDialogResultListener(circleImageViewProfilePhoto)
        formatMobilePhoneInput(editTextMobilePhone)
        formatDateOfBirthday(editTextDateOfBirthday)
    }

    private fun setUserInfo() = with(binding) {
        viewModel.setEvent(EditProfileContract.Event.SetUserInfo(args.userServerId))
    }


    @SuppressLint("ClickableViewAccessibility")
    private fun setListeners() = with(binding) {
        buttonSave.setOnClickListener {

            viewModel.setEvent(
                EditProfileContract.Event.OnSaveButtonClicked(
                    userServerId = args.userServerId,
                    updateUserData =
                        UpdateUserData(
                            name = getTextFromEditText(editTextUsername),
                            career = getTextFromEditText(editTextCareer),
                            address = getTextFromEditText(editTextAddress),
                            phone = getTextFromEditText(editTextMobilePhone),
                            birthday = reversDateFormatting(
                                getTextFromEditText(editTextDateOfBirthday)
                            ),
                        )
                )
            )
        }
        imageButtonAddProfilePhoto.setOnClickListener {
            viewModel.setEvent(EditProfileContract.Event.OnAddProfilePhotoImageViewClicked)
        }

        editTextDateOfBirthday.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                setDate(editTextDateOfBirthday::setText)
                true
            } else {
                false
            }
        }
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


    private fun dateFormating(date: Date): String {
        val dateFormat = "dd/MM/yyyy"
        val simpleDateFormat = SimpleDateFormat(dateFormat, Locale.getDefault())
        return simpleDateFormat.format(date)
    }

    private fun reversDateFormatting(date: String): Date? {
        val dateFormat = "dd/MM/yyyy"
        val simpleDateFormat = SimpleDateFormat(dateFormat, Locale.getDefault())
        return simpleDateFormat.parse(date)
    }

    private fun setObservers() = with(binding) {
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

            progressBarRequest.isVisible = state.isProgressBarShowed
            setLoadingState(state.isProgressBarShowed, binding)
        }

        collectFlow(viewModel.effect) { effect ->
            when (effect) {
                is EditProfileContract.Effect.NavigateToChooseProfilePhotoDialog -> moveToChooseProfilePhotoDialog()
                is EditProfileContract.Effect.NavigateToMyProfileScreen -> {
                    moveBackToMyProfileScreen()
                }
            }
        }
    }

    fun moveBackToMyProfileScreen() {
        findNavController().navigateUp()
    }

    private fun moveToChooseProfilePhotoDialog() {
        val direction =
            EditProfileFragmentDirections.actionEditProfileFragmentToChooseProfilePhotoDialog()
        findNavController().navigate(direction)
    }

    private fun formatDateOfBirthday(editText: EditText) {
        editText.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}

            override fun onTextChanged(
                inputText: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                inputText?.let {

                    if (HYPHEN_POSITIONS.contains(inputText.length) && count >= 0 && before == 0) {
                        updateData(editText)

                    }
                }
            }
        })
    }

    private fun updateData(editText: EditText) {
        val currentText = editText.text.toString()
        val newInputText = StringBuilder(currentText)
        newInputText.insert(currentText.length - 1, "/")
        editText.setText(newInputText)
        editText.setSelection(editText.length())
    }

    companion object {

        private val HYPHEN_POSITIONS = listOf(3, 6)
    }

}