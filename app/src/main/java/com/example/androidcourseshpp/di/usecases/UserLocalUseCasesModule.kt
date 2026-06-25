package com.example.androidcourseshpp.di.usecases

import com.example.androidcourseshpp.domain.repository.UserLocalRepository
import com.example.androidcourseshpp.domain.usecase.userlocal.GetUserAvatarUseCase
import com.example.androidcourseshpp.domain.usecase.userlocal.GetUserServerIdUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UserLocalUseCasesModule {

    @Singleton
    @Provides
    fun provideGetUserAvatarUseCase(userLocalRepository: UserLocalRepository) =
        GetUserAvatarUseCase(userLocalRepository)

    @Singleton
    @Provides
    fun provideGetUserIdUseCase(userLocalRepository: UserLocalRepository) =
        GetUserServerIdUseCase(userLocalRepository)
}

