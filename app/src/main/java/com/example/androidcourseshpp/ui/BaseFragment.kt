package com.example.androidcourseshpp.ui

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewbinding.ViewBinding
import com.example.androidcourseshpp.R
import com.example.androidcourseshpp.databinding.FragmentEditProfileBinding
import com.example.androidcourseshpp.databinding.FragmentSignInBinding
import com.example.androidcourseshpp.databinding.FragmentSignUpExtendedBinding
import com.example.androidcourseshpp.ui.screens.main.MainActivity
import com.example.androidcourseshpp.ui.screens.main.chooseprofilephotodialog.ChooseProfilePhotoDialog
import com.example.androidcourseshpp.ui.screens.main.chooseprofilephotodialog.ChooseProfilePhotoDialog.Companion.PHOTO
import com.example.androidcourseshpp.ui.screens.model.UserUIModel
import com.example.androidcourseshpp.ui.utils.loadImageFromURL
import com.example.androidcourseshpp.ui.utils.onChangeTextListener
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

const val USER_INFO = "userInfo"


abstract class BaseFragment<VBinding : ViewBinding>(
    private val inflaterMethod: (LayoutInflater, ViewGroup?, Boolean) -> VBinding
) : Fragment() {
    private var _binding: VBinding? = null
    val binding get() = requireNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflaterMethod.invoke(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    open fun setObservers() {}

    open fun setListeners() {}

    protected fun <T> BaseFragment<VBinding>.collectFlow(flow: Flow<T>, onCollect: (T) -> Unit) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                flow.collect {
                    onCollect(it)
                }
            }
        }
    }

    protected fun makeToast(messageResId: Int) {
        Toast.makeText(requireContext(), messageResId, Toast.LENGTH_LONG).show()
    }

    protected fun TextView.updateIfNotEmpty(newValue: String) {
        if (newValue.isNotEmpty()) {
            text = newValue
        }
    }

    protected fun moveToUserProfileScreen(userInfo: UserUIModel) {
        val intent = Intent(requireContext(), MainActivity::class.java)

        intent.putExtra(USER_INFO, userInfo)

        val options = ActivityOptions.makeCustomAnimation(
            requireContext(),
            R.anim.slide_in_from_right_to_left,
            R.anim.slide_out_from_right_to_left
        )

        startActivity(intent, options.toBundle())
        requireActivity().finish()
    }

    protected fun formatMobilePhoneInput(editText: EditText) {
        editText.onChangeTextListener { inputText, _, before, count ->
            if (BRACKET_POSITIONS.keys.contains(inputText.length) && count > 0 && before == 0) {
                updateMobilePhoneInput(BRACKET_POSITIONS[inputText.length], editText)
            }
            if (HYPHEN_POSITIONS.contains(inputText.length) && count > 0 && before == 0) {
                updateMobilePhoneInput("-", editText)

            }
        }
    }

    private fun updateMobilePhoneInput(sign: String?, editText: EditText) {
        val currentText = editText.text.toString()
        val newInputText = StringBuilder(currentText)
        newInputText.insert(currentText.length - 1, sign)
        editText.setText(newInputText)
        editText.setSelection(editText.length())
    }

    protected fun setChooseProfilePhotoDialogResultListener(
        imageView: ImageView,
        updateState: (String) -> Unit
    ) {
        parentFragmentManager.setFragmentResultListener(
            ChooseProfilePhotoDialog.REQUEST_KEY,
            viewLifecycleOwner
        ) { _, data ->
            val profilePhoto = data.getString(PHOTO) ?: ""
            imageView.loadImageFromURL(requireContext(), profilePhoto)
            updateState(profilePhoto)
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