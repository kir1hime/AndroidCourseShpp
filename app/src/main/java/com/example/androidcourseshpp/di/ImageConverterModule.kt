package com.example.androidcourseshpp.di

import com.example.androidcourseshpp.ui.utils.imageconvertor.ImageConverter
import com.example.androidcourseshpp.ui.utils.imageconvertor.ImageConvertorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface ImageConverterModule {

    @Binds
    @Singleton
    fun bindImageConverter(imageConverterImpl: ImageConvertorImpl): ImageConverter
}