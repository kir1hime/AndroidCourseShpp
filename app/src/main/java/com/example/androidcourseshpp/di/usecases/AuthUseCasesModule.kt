package com.example.androidcourseshpp.di.usecases

import com.example.androidcourseshpp.domain.repository.AuthRepository
import com.example.androidcourseshpp.domain.repository.ContactsLocalRepository
import com.example.androidcourseshpp.domain.repository.GalleryRepository
import com.example.androidcourseshpp.domain.repository.UserLocalDataRepository
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
        userLocalDataRepository: UserLocalDataRepository,
        galleryRepository: GalleryRepository,
        contactsLocalRepository: ContactsLocalRepository
    ) = LogOutUseCase(
        authRepository = authRepository,
        userLocalDataRepository = userLocalDataRepository,
        galleryRepository = galleryRepository,
        contactsLocalRepository = contactsLocalRepository
    )

    @Singleton
    @Provides
    fun provideSingInUseCase(
        authRepository: AuthRepository,
        userLocalDataRepository: UserLocalDataRepository
    ) = SignInUseCase(
        authRepository = authRepository,
        userLocalDataRepository = userLocalDataRepository
    )

    @Singleton
    @Provides
    fun provideSignUpUseCase(
        authRepository: AuthRepository,
        userLocalDataRepository: UserLocalDataRepository
    ) = SignUpUseCase(
        authRepository = authRepository,
        userLocalDataRepository = userLocalDataRepository
    )
}