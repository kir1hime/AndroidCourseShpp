package com.example.androidcourseshpp.di.usecases

import com.example.androidcourseshpp.domain.repository.GalleryRepository
import com.example.androidcourseshpp.domain.usecase.gallery.AddGalleryPhotoUseCase
import com.example.androidcourseshpp.domain.usecase.gallery.AddGalleryPhotoUseCaseImpl
import com.example.androidcourseshpp.domain.usecase.gallery.GetGalleryPhotosUseCase
import com.example.androidcourseshpp.domain.usecase.gallery.GetGalleryPhotosUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class GalleryUseCasesProvideModule {

    @Singleton
    @Provides
    fun provideAddGalleryPhotoUseCase(galleryRepository: GalleryRepository) =
        AddGalleryPhotoUseCaseImpl(galleryRepository)

    @Singleton
    @Provides
    fun provideGetGalleryPhotsUseCase(galleryRepository: GalleryRepository) =
        GetGalleryPhotosUseCaseImpl(galleryRepository)
}

@Module
@InstallIn(SingletonComponent::class)
interface GalleryUSeCasesBindModule {

    @Binds
    fun bindAddGalleryPhotoUseCase(
        addGalleryPhotoUseCaseImpl: AddGalleryPhotoUseCaseImpl
    ): AddGalleryPhotoUseCase

    @Binds
    fun bindGetGalleryPhotosUseCase(
        getGalleryPhotosUseCaseImpl: GetGalleryPhotosUseCaseImpl
    ): GetGalleryPhotosUseCase
}