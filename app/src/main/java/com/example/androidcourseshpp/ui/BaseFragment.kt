package com.example.androidcourseshpp.ui

import android.app.ActivityOptions
import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.example.androidcourseshpp.R
import com.example.androidcourseshpp.databinding.FragmentEditProfileBinding
import com.example.androidcourseshpp.databinding.FragmentSignInBinding
import com.example.androidcourseshpp.databinding.FragmentSignUpBinding
import com.example.androidcourseshpp.databinding.FragmentSignUpExtendedBinding
import com.example.androidcourseshpp.ui.extensions.loadImageFromURL
import com.example.androidcourseshpp.ui.screens.MainActivity
import com.example.androidcourseshpp.ui.screens.chooseprofilephotodialog.ChooseProfilePhotoDialog
import com.example.androidcourseshpp.ui.screens.chooseprofilephotodialog.ChooseProfilePhotoDialog.Companion.PHOTO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

const val USER_SERVER_ID = "userServerId"
const val RESULT_KEY = "resultPreviousScreenKey"

open class BaseFragment : Fragment() {

    fun <T> BaseFragment.collectFlow(flow: Flow<T>, onCollect: (T) -> Unit) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                flow.collect {
                    onCollect(it)
                }
            }
        }
    }

    fun makeToast(messageResId: Int) {
        Toast.makeText(requireContext(), messageResId, Toast.LENGTH_LONG).show()
    }

    fun moveToMyProfileScreen(userServerId: Long) {
        val intent = Intent(requireContext(), MainActivity::class.java)

        intent.putExtra(USER_SERVER_ID, userServerId)

        val options = ActivityOptions.makeCustomAnimation(
            requireContext(),
            R.anim.slide_in_from_right_to_left,
            R.anim.slide_out_from_right_to_left
        )

        startActivity(intent, options.toBundle())
        requireActivity().finish()
    }

    protected fun formatMobilePhoneInput(editText: EditText) {
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

                    if (BRACKET_POSITIONS.keys.contains(inputText.length) && count > 0 && before == 0) {
                        updateMobilePhoneInput(BRACKET_POSITIONS[inputText.length], editText)
                    }
                    if (HYPHEN_POSITIONS.contains(inputText.length) && count > 0 && before == 0) {
                        updateMobilePhoneInput("-", editText)

                    }
                }
            }
        })
    }

    private fun updateMobilePhoneInput(sign: String?, editText: EditText) {
        val currentText = editText.text.toString()
        val newInputText = StringBuilder(currentText)
        newInputText.insert(currentText.length - 1, sign)
        editText.setText(newInputText)
        editText.setSelection(editText.length())
    }

    protected fun <T> setResultForPreviousScreen(data: T) {
        findNavController().previousBackStackEntry?.savedStateHandle?.set(
            RESULT_KEY,
            data
        )
    }

    protected fun <T> setResultListener(): MutableLiveData<T>? {
        return findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData(RESULT_KEY)
    }


    protected fun setChooseProfilePhotoDialogResultListener(imageView: ImageView) {
        parentFragmentManager.setFragmentResultListener(
            ChooseProfilePhotoDialog.REQUEST_KEY,
            viewLifecycleOwner
        ) { _, data ->
            val newProfilePhoto = data.getString(PHOTO) ?: ""
            imageView.loadImageFromURL(requireContext(), newProfilePhoto)
        }
    }

    protected fun <T : ViewBinding> setLoadingState(isLoaded: Boolean, binding: T) {
        val isEnabled = !isLoaded

        when (binding) {
            is FragmentSignInBinding -> with(binding) {
                enableEditText(editTextEMail)
                enableEditText(editTextPassword)
                comboBoxRememberMe.isClickable = isEnabled
            }

            is FragmentEditProfileBinding -> with(binding) {
                enableEditText(editTextUsername)
                enableEditText(editTextCareer)
                enableEditText(editTextMobilePhone)
                enableEditText(editTextAddress)
                enableEditText(editTextDateOfBirthday)
                imageButtonAddProfilePhoto.isClickable = isEnabled
            }

            is FragmentSignUpExtendedBinding -> with(binding) {
                enableEditText(editTextUserName)
                enableEditText(editTextMobilePhone)
                imageButtonAddProfilePhoto.isClickable = isEnabled
            }
        }
    }

    private fun enableEditText(editText: EditText) {
        editText.apply {
            isFocusable = isEnabled
            isFocusableInTouchMode = isEnabled
        }
    }

    protected fun getTextFromEditText(editText: EditText) = editText.text.toString()

    companion object {
        private val BRACKET_POSITIONS = mapOf(1 to "(", 5 to ")-")
        private val HYPHEN_POSITIONS = listOf(6, 10, 13)
    }

}