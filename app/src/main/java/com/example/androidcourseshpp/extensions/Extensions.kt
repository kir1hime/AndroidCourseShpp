package com.example.androidcourseshpp.extensions

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.core.view.ViewCompat
import androidx.core.view.ViewCompat.setOnApplyWindowInsetsListener
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.ViewTarget
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

fun Class<Glide>.loadImageFromURL(context: Context, url:String, ava:CircleImageView): ViewTarget<ImageView, Drawable> {
    return Glide.with(context).load(url).into(ava)
}

fun Class<Picasso>.loadImageFromURL(context: Context, url: String, ava: CircleImageView){
   return  Picasso.get().load(url).into(ava)
}

fun Class<ViewCompat>.adaptedUserInterface(view: View){
    setOnApplyWindowInsetsListener(view) { v, insets ->
        val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
        v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
        insets
    }
}


