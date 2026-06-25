package com.example.androidcourseshpp.di.usecases

import com.example.androidcourseshpp.domain.repository.GalleryRepository
import com.example.androidcourseshpp.domain.usecase.gallery.AddGalleryPhotoUseCase
import com.example.androidcourseshpp.domain.usecase.gallery.GetGalleryPhotosUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class GalleryUseCasesModule {

    @Singleton
    @Provides
    fun provideAddGalleryPhotoUseCase(galleryRepository: GalleryRepository) =
        AddGalleryPhotoUseCase(galleryRepository)

    @Singleton
    @Provides
    fun provideGetGalleryPhotsUseCase(galleryRepository: GalleryRepository) =
        GetGalleryPhotosUseCase(galleryRepository)
}