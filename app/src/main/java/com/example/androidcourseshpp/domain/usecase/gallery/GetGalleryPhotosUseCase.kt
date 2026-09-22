package com.example.androidcourseshpp.domain.usecase.gallery

import com.example.androidcourseshpp.domain.entity.gallery.GalleryItemInfo
import com.example.androidcourseshpp.domain.repository.GalleryRepository
import kotlinx.coroutines.flow.Flow

interface GetGalleryPhotosUseCase {
    operator fun invoke(): Flow<List<GalleryItemInfo>>
}

class GetGalleryPhotosUseCaseImpl(
    private val galleryRepository: GalleryRepository
) : GetGalleryPhotosUseCase {

    override operator fun invoke(): Flow<List<GalleryItemInfo>> = galleryRepository.galleryPhotos

}