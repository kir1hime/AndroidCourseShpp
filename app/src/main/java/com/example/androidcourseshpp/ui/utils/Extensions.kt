package com.example.androidcourseshpp.ui.utils

import android.content.Context
import android.net.Uri
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.ImageView
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.example.androidcourseshpp.R

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


