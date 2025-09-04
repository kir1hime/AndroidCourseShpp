package com.example.androidcourseshpp.ui.extensions

import android.content.Context
import android.view.View
import android.widget.ImageView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.androidcourseshpp.R


fun adaptUserInterface(view: View) {
    ViewCompat.setOnApplyWindowInsetsListener(view) { v, insets ->
        val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
        v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
        insets
    }
}

fun ImageView.loadImageFromURL(
    context: Context,
    url: String,
    placeholder: Int = R.drawable.ic_defaultavatar1
) {
    useGlideForLoading(context, url, this, placeholder)
}

private fun useGlideForLoading(context: Context, url: String, view: ImageView, placeholder: Int) {
    Glide.with(context)
        .load(url)
        .placeholder(placeholder)
        .circleCrop()
        .into(view)
}



