package com.example.androidcourseshpp.domain.usecase.gallery

import com.example.androidcourseshpp.domain.repository.GalleryRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AddGalleryPhotoUseCase @Inject constructor(private val galleryRepository: GalleryRepository) {

    operator fun invoke(photo: String) {
        galleryRepository.addPhoto(photo)
    }

}