package com.example.androidcourseshpp.domain.usecase.gallery

import com.example.androidcourseshpp.domain.entity.gallery.GalleryItemInfo
import com.example.androidcourseshpp.domain.repository.GalleryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetGalleryPhotosUseCase @Inject constructor(private val galleryRepository: GalleryRepository) {

    operator fun invoke(): Flow<List<GalleryItemInfo>> = galleryRepository.galleryPhotos

}