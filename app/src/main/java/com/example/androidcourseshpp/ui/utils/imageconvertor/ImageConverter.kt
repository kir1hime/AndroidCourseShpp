package com.example.androidcourseshpp.ui.utils.imageconvertor

import android.graphics.Bitmap
import okhttp3.MultipartBody

interface ImageConverter {
    fun convertBitmapToMultipartBody(bitmap: Bitmap): MultipartBody.Part
}