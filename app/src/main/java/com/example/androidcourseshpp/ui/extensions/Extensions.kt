package com.example.androidcourseshpp.ui.extensions

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.androidcourseshpp.R
import com.example.androidcourseshpp.data.ImageLoader
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.CropCircleTransformation



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


