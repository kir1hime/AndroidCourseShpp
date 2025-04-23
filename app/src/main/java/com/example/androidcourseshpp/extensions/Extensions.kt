package com.example.androidcourseshpp.extensions

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.target.ViewTarget
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

fun Class<Glide>.get(context: Context): RequestManager {
    return Glide.with(context)
}

fun Class<Glide>.loadImageFromURL(context: Context, url:String, ava:CircleImageView): ViewTarget<ImageView, Drawable> {
    return Glide.with(context).load(url).into(ava)
}

fun Class<Picasso>.get(context: Context): Picasso {
    return Picasso.get()
}

fun Class<Picasso>.loadImageFromURL(context: Context, url: String, ava: CircleImageView){
   return  Picasso.get().load(url).into(ava)
}


