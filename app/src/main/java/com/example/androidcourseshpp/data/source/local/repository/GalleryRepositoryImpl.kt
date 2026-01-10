package com.example.androidcourseshpp.data.source.local.repository

import com.example.androidcourseshpp.data.source.local.userdata.UserDataProvider
import com.example.androidcourseshpp.ui.screens.main.chooseprofilephotodialog.entity.GalleryItem
import com.example.androidcourseshpp.domain.repository.GalleryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.collections.forEach

@Singleton
class GalleryRepositoryImpl @Inject constructor(private val userDataProvider: UserDataProvider) :
    GalleryRepository {

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
    override val galleryPhotos: StateFlow<List<GalleryItem>> get() = _galleryPhotos.asStateFlow()

    override fun addPhoto(photoURL: String) {
        val newItem = GalleryItem(id = _galleryPhotos.value.size + 1, photoURL = photoURL)

        _galleryPhotos.update { photoList ->
            val newList = photoList.toMutableList()

            photoList.forEach { photo ->
                if (photo.photoURL == newItem.photoURL) {
                    return
                }
            }

            newList.add(0, newItem)
            return@update newList
        }

        val photoURLSet = mutableSetOf<String>()
        _galleryPhotos.value.map { photo -> photoURLSet.add(photo.photoURL) }

        userDataProvider.saveUserGalleryPhotos(photoURLSet)
    }

    private fun getDefaultGalleryItems(): List<GalleryItem> {
        val galleryItemList = mutableListOf<GalleryItem>()

        if (userDataProvider.getUserGalleryPhotos().isEmpty()) {
            repeat(defaultStringPhotos.size) { index ->
                galleryItemList.add(GalleryItem(index, defaultStringPhotos[index]))
            }
        } else {
            userDataProvider.getUserGalleryPhotos().mapIndexed { index, photoURL ->
                galleryItemList.add(GalleryItem(index, photoURL))
            }
        }

        return galleryItemList
    }
}