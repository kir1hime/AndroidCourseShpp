package com.example.androidcourseshpp.ui.utils

import android.content.Context
import android.net.Uri
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.example.androidcourseshpp.R
import com.example.androidcourseshpp.domain.utils.AppError
import com.example.androidcourseshpp.domain.utils.Result
import kotlinx.coroutines.launch

fun ImageView.loadImageFromURLCircled(
    context: Context,
    url: String,
    placeholder: Int = R.drawable.profile_mockup
) {
    Glide.with(context)
        .load(url)
        .placeholder(placeholder)
        .circleCrop()
        .into(this)
}

fun ImageView.loadImageFromURL(
    context: Context,
    url: String,
    placeholder: Int = R.drawable.profile_mockup
) {
    Glide.with(context)
        .load(url)
        .placeholder(placeholder)
        .into(this)
}

fun EditText.onChangeTextListener(onTextChanged: (CharSequence, Int, Int, Int) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun afterTextChanged(s: Editable?) {}

        override fun onTextChanged(
            inputText: CharSequence?,
            start: Int,
            before: Int,
            count: Int
        ) {
            inputText?.let {
                onTextChanged(inputText, start, before, count)
            }
        }
    })
}

fun Int.toUri(context: Context): Uri {
    return "android.resource://${context.packageName}/${this}".toUri()
}

fun String.isContainsOrderedSequence(searched: String): Boolean {
    val lowerCaseSearched = searched.lowercase().trim()
    val lowercaseSource = this.lowercase()

    var subSource = lowercaseSource
    var searchedCounter = 0

    for (char in lowerCaseSearched.withIndex()) {
        if (subSource.contains(char.value)) {

            subSource = subSource.substring(subSource.indexOf(char.value) + 1, subSource.length)
            searchedCounter++
        }
    }
    return lowerCaseSearched.length == searchedCounter
}

fun <T> ViewModel.executeUseCase(
    toExecute: suspend () -> Result<T>,
    onSuccess: (T) -> Unit = {},
    onBackendError: () -> Unit = {},
    onConnectionError: () -> Unit = {},
    onResponseProcessingError: () -> Unit = {},
    onLocalStorageError: () -> Unit = {},
    onError: () -> Unit = {},
    finally: () -> Unit = {}
) {
    viewModelScope.launch {
        when (val result = toExecute()) {
            is Result.Success -> {
                onSuccess(result.data)
            }

            is Result.Error -> {
                onError()
                when (result.error) {
                    is AppError.BackendError -> onBackendError()
                    is AppError.ConnectionError -> onConnectionError()
                    is AppError.LocalStorageError -> onLocalStorageError()
                    is AppError.ResponseProcessingError -> onResponseProcessingError()
                }
            }
        }
        finally()
    }
}
