package com.example.androidcourseshpp.contactlist

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.squareup.picasso.Picasso

fun Class<Glide>.get(context: Context): RequestManager {
    return Glide.with(context)
}

fun Class<Picasso>.get(context: Context): Picasso {
    return Picasso.get()
}



