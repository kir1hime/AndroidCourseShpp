package com.example.androidcourseshpp.domain.usecase.gallery

import com.example.androidcourseshpp.domain.repository.GalleryRepository

interface AddGalleryPhotoUseCase {
    suspend operator fun invoke(photo: String)
}

class AddGalleryPhotoUseCaseImpl(
    private val galleryRepository: GalleryRepository
) : AddGalleryPhotoUseCase {

    override suspend operator fun invoke(photo: String) {
        galleryRepository.addPhoto(photo)
    }

}