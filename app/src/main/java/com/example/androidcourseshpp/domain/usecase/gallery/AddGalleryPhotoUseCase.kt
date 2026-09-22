package com.example.androidcourseshpp.domain.usecase.gallery

import com.example.androidcourseshpp.domain.repository.GalleryRepository

interface AddGalleryPhotoUseCase {
    operator fun invoke(photo: String)
}

class AddGalleryPhotoUseCaseImpl(
    private val galleryRepository: GalleryRepository
) : AddGalleryPhotoUseCase {

    override operator fun invoke(photo: String) {
        galleryRepository.addPhoto(photo)
    }

}