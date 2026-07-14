package com.example.androidcourseshpp.ui.utils

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.ImageView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.Navigator
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
    return Uri.Builder()
        .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
        .authority(context.packageName)
        .appendPath(this.toString())
        .build()
}

fun String.containsOrderedSequence(searched: String): Boolean {
    val cleanSearched = searched.lowercase().trim()
    val source = this.lowercase()
    var indexInSource = 0
    for (char in cleanSearched) {
        indexInSource = source.indexOf(char, indexInSource)

        if (indexInSource == -1) return false
        indexInSource++
    }

    return true
}

fun NavController.navigate(
    directions: NavDirections,
    extras: Navigator.Extras?
) {
    if (extras != null) {
        navigate(directions, extras)
    } else {
        navigate(directions)
    }
}

fun <T> ViewModel.executeUseCase(
    toExecute: suspend () -> Result<T>,
    onSuccess: ((T) -> Unit)? = null,
    onBackendError: (() -> Unit)? = null,
    onConnectionError: (() -> Unit)? = null,
    onResponseProcessingError: (() -> Unit)? = null,
    onLocalStorageError: (() -> Unit)? = null,
    onError: (() -> Unit)? = null,
    onRemoteError: (() -> Unit)? = null,
    finally: (() -> Unit)? = null
) {
    viewModelScope.launch {
        when (val result = toExecute()) {
            is Result.Success -> {
                onSuccess?.invoke(result.data)
            }
            is Result.Error -> {
                onError?.let {
                    it.invoke()
                    finally?.invoke()
                    return@launch
                }
                onRemoteError?.let {
                    if (result.error is AppError.BackendError || result.error is AppError.ResponseProcessingError) {
                        it.invoke()
                        finally?.invoke()
                        return@launch
                    }
                }
                when (result.error) {
                    is AppError.BackendError -> onBackendError?.invoke()
                    is AppError.ConnectionError -> onConnectionError?.invoke()
                    is AppError.LocalStorageError -> onLocalStorageError?.invoke()
                    is AppError.ResponseProcessingError -> onResponseProcessingError?.invoke()
                }
            }
        }
        finally?.invoke()
    }
}
