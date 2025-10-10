package com.example.androidcourseshpp.data.gallery

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GalleryRepository @Inject constructor() {

    private val defaultStringPhotos: List<String> = listOf(
        "https://funny.klev.club/uploads/posts/2024-03/funny-klev-club-p-smeshnie-kartinki-koti-na-avu-1.jpg",
        "https://kartinki.pics/uploads/posts/2022-02/1645750181_1-kartinkin-net-p-kartinki-milie-sobachki-1.jpg",
        "https://kartinki.pics/uploads/posts/2022-02/1645235615_4-kartinkin-net-p-kroliki-kartinki-4.jpg",
        "https://i.pinimg.com/736x/ca/f7/be/caf7bece7bb9a056bc5f89ea6ef5e900.jpg",
        "https://i.pinimg.com/originals/b1/cc/99/b1cc9987043f82eda1963ab8ba5d03c5.jpg",
        "https://funny.klev.club/smeh/uploads/posts/2024-05/funny-klev-club-jo4j-p-smeshnie-kartinki-popugaya-na-avu-3.jpg",
        "https://funny.klev.club/uploads/posts/2024-03/funny-klev-club-p-smeshnie-kartinki-khomyaki-na-avu-8.jpg"
    )
    private val _galleryPhotos = MutableStateFlow(getDefaultGalleryItems())
    val galleryPhotos: StateFlow<List<GalleryItem>> get()=  _galleryPhotos.asStateFlow()

    private fun getDefaultGalleryItems(): List<GalleryItem>{
        val galleryItemList = mutableListOf<GalleryItem>()

        repeat(defaultStringPhotos.size){ index ->
            galleryItemList.add(GalleryItem(index, defaultStringPhotos[index]))
        }

        return galleryItemList
    }
}