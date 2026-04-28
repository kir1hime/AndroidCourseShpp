package com.example.androidcourseshpp.ui.utils


import android.content.Context
import android.graphics.Bitmap
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject
import javax.inject.Singleton

const val QUALITY_OF_JPEG_FILE = 90

@Singleton
class ImageConvertor @Inject constructor(@ApplicationContext private val context: Context) {

    fun convertBitmapToMultipartBody(bitmap: Bitmap): MultipartBody.Part {
        val file = convertBitmapToFile(bitmap)
        return convertFileToMultipartBody(file)
    }

    private fun convertBitmapToFile(bitmap: Bitmap): File {
        val file = File(context.cacheDir, "user_avatar.jpg")

        FileOutputStream(file).use { outputStream ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, QUALITY_OF_JPEG_FILE, outputStream)
        }

        return file

    }

    private fun convertFileToMultipartBody(file: File): MultipartBody.Part {
        val requestFile = file.asRequestBody("image/jpeg".toMediaType())
        return MultipartBody.Part.createFormData("image", file.name, requestFile)
    }
}
