package com.example.androidcourseshpp.ui.extensions

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.androidcourseshpp.R

fun ImageView.loadImageFromURL(
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


