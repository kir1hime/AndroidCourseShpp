package com.example.androidcourseshpp.di.usecases

import com.example.androidcourseshpp.domain.repository.AuthRepository
import com.example.androidcourseshpp.domain.repository.GalleryRepository
import com.example.androidcourseshpp.domain.repository.UserLocalRepository
import com.example.androidcourseshpp.domain.usecase.auth.LogOutUseCase
import com.example.androidcourseshpp.domain.usecase.auth.SignInUseCase
import com.example.androidcourseshpp.domain.usecase.auth.SignUpUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AuthUseCasesModule {

    @Singleton
    @Provides
    fun provideLogOutUseCase(
        authRepository: AuthRepository,
        userLocalRepository: UserLocalRepository,
        galleryRepository: GalleryRepository
    ) = LogOutUseCase(
        authRepository = authRepository,
        userLocalRepository = userLocalRepository,
        galleryRepository = galleryRepository
    )

    @Singleton
    @Provides
    fun provideSingInUseCase(
        authRepository: AuthRepository,
        userLocalRepository: UserLocalRepository
    ) = SignInUseCase(
        authRepository = authRepository,
        userLocalRepository = userLocalRepository
    )

    @Singleton
    @Provides
    fun provideSignUpUseCase(
        authRepository: AuthRepository,
        userLocalRepository: UserLocalRepository
    ) = SignUpUseCase(
        authRepository = authRepository,
        userLocalRepository = userLocalRepository
    )
}