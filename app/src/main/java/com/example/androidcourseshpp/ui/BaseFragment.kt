package com.example.androidcourseshpp.ui

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

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

    protected fun <T> setResultListener() : MutableLiveData<T>? {
        return findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData(RESULT_KEY)
    }

    companion object {
        private val BRACKET_POSITIONS = mapOf(1 to "(", 5 to ")-")
        private val HYPHEN_POSITIONS = listOf(6, 10, 13)
    }

}