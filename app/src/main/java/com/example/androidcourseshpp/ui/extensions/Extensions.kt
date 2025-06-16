package com.example.androidcourseshpp.ui.extensions

import android.content.Context
import android.view.View
import android.widget.ImageView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.androidcourseshpp.R
import com.example.androidcourseshpp.data.ImageLoader
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.CropCircleTransformation


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
    imageLoader: ImageLoader,
    placeholder: Int = R.drawable.ic_defaultavatar1
) {
    when (imageLoader) {
        ImageLoader.GLIDE -> useGlideForLoading(context, url, this, placeholder)
        ImageLoader.PICASSO -> usePicassoForLoading(url, this, placeholder)
    }
}

private fun useGlideForLoading(context: Context, url: String, view: ImageView, placeholder: Int) {
    Glide.with(context)
        .load(url)
        .placeholder(placeholder)
        .circleCrop()
        .into(view)
}

private fun usePicassoForLoading(url: String, view: ImageView, placeholder: Int) {
    Picasso.get()
        .load(url)
        .placeholder(placeholder)
        .transform(CropCircleTransformation())
        .into(view)
}


