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

fun ImageView.loadImageFromURL(context: Context, url: String, imageLoader: ImageLoader) {
    when (imageLoader) {
        ImageLoader.GLIDE -> useGlideForLoading(context, url, this)
        ImageLoader.PICASSO -> usePicassoForLoading(url, this)
    }
}

private fun useGlideForLoading(context: Context, url: String, view: ImageView) {
    Glide.with(context)
        .load(url)
        .placeholder(R.drawable.ic_defaultavatar)
        .circleCrop()
        .into(view)
}

private fun usePicassoForLoading(url: String, view: ImageView) {
    Picasso.get()
        .load(url)
        .placeholder(R.drawable.ic_defaultavatar)
        .transform(CropCircleTransformation())
        .into(view)
}


