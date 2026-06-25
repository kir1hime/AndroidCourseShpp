package com.example.androidcourseshpp.domain.usecase.gallery

import com.example.androidcourseshpp.domain.repository.GalleryRepository


class AddGalleryPhotoUseCase(private val galleryRepository: GalleryRepository) {

    operator fun invoke(photo: String) {
        galleryRepository.addPhoto(photo)
    }

}