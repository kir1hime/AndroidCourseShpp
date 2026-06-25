package com.example.androidcourseshpp.domain.usecase.gallery

import com.example.androidcourseshpp.domain.entity.gallery.GalleryItemInfo
import com.example.androidcourseshpp.domain.repository.GalleryRepository
import kotlinx.coroutines.flow.Flow


class GetGalleryPhotosUseCase(private val galleryRepository: GalleryRepository) {

    operator fun invoke(): Flow<List<GalleryItemInfo>> {
        val photos: Flow<List<GalleryItemInfo>> =
            galleryRepository.galleryPhotos

        return photos
    }

}