package com.example.androidcourseshpp.data.models.gallery

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GalleryRepository @Inject constructor() {

    private val defaultStringPhotos: List<String> = listOf(
        "https://cdn.images.express.co.uk/img/dynamic/130/940x/secondary/Cavalier-King-Charles-Spaniel-5464056.jpg?r=1722869239639",
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS77-bE3-x2EPmO7VTl1luWWrhRl5t0-76TKg&s",
        "https://miro.medium.com/0*A7MUqyCLvZDcHkfM.jpg",
        "https://i.pinimg.com/736x/5f/7f/1a/5f7f1aa0f0fbd26f5e8a2aff6032b901.jpg",
        "https://i.redd.it/tvl1jecyywz51.jpg",
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTbF6SxQ2IiXHTlGCONXbt4G1HVovKvEOxCCrt93hOXUNwq_kfP9d4l24K9uPhjRoWxzO4&usqp=CAU",
        "https://i.pinimg.com/736x/b6/a6/d5/b6a6d50de7eb36065b98ebd254d46cd5.jpg"
    )
    private val _galleryPhotos = MutableStateFlow(getDefaultGalleryItems())
    val galleryPhotos: StateFlow<List<GalleryItem>> get() = _galleryPhotos.asStateFlow()

    private fun getDefaultGalleryItems(): List<GalleryItem> {
        val galleryItemList = mutableListOf<GalleryItem>()

        repeat(defaultStringPhotos.size) { index ->
            galleryItemList.add(GalleryItem(index, defaultStringPhotos[index]))
        }

        return galleryItemList
    }
}