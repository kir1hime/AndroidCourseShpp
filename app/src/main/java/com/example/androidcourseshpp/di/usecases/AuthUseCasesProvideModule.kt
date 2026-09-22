package com.example.androidcourseshpp.di.usecases

import com.example.androidcourseshpp.domain.repository.AuthRepository
import com.example.androidcourseshpp.domain.repository.ContactsLocalRepository
import com.example.androidcourseshpp.domain.repository.GalleryRepository
import com.example.androidcourseshpp.domain.repository.UserLocalDataRepository
import com.example.androidcourseshpp.domain.usecase.auth.LogOutUseCase
import com.example.androidcourseshpp.domain.usecase.auth.LogOutUseCaseImpl
import com.example.androidcourseshpp.domain.usecase.auth.SignInUseCase
import com.example.androidcourseshpp.domain.usecase.auth.SignInUseCaseImpl
import com.example.androidcourseshpp.domain.usecase.auth.SignUpUseCase
import com.example.androidcourseshpp.domain.usecase.auth.SignUpUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AuthUseCasesProvideModule {

    @Singleton
    @Provides
    fun provideLogOutUseCase(
        authRepository: AuthRepository,
        userLocalDataRepository: UserLocalDataRepository,
        galleryRepository: GalleryRepository,
        contactsLocalRepository: ContactsLocalRepository
    ) = LogOutUseCaseImpl(
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
    ) = SignInUseCaseImpl(
        authRepository = authRepository,
        userLocalDataRepository = userLocalDataRepository
    )

    @Singleton
    @Provides
    fun provideSignUpUseCase(
        authRepository: AuthRepository,
        userLocalDataRepository: UserLocalDataRepository
    ) = SignUpUseCaseImpl(
        authRepository = authRepository,
        userLocalDataRepository = userLocalDataRepository
    )
}

@Module
@InstallIn(SingletonComponent::class)
interface AuthUseCasesBindModule {

    @Binds
    fun bindSignInUseCase(
        signInUseCaseImpl: SignInUseCaseImpl
    ): SignInUseCase

    @Binds
    fun bindSignUpUseCase(
        signUpUseCaseImpl: SignUpUseCaseImpl
    ): SignUpUseCase

    @Binds
    fun bindLogOutUseCase(
        logOutUseCaseImpl: LogOutUseCaseImpl
    ): LogOutUseCase
}