package com.example.androidcourseshpp.ui.utils

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.androidcourseshpp.R

fun ImageView.loadImageFromURLCircled(
    context: Context,
    url: String,
    placeholder: Int = R.drawable.ic_defaultavatar1
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
    placeholder: Int = R.drawable.ic_defaultavatar1
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


