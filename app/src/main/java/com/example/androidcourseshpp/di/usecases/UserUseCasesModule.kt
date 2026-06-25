package com.example.androidcourseshpp.di.usecases

import com.example.androidcourseshpp.domain.repository.UserLocalRepository
import com.example.androidcourseshpp.domain.repository.UserRepository
import com.example.androidcourseshpp.domain.usecase.user.GetUserUseCase
import com.example.androidcourseshpp.domain.usecase.user.GetUsersUseCase
import com.example.androidcourseshpp.domain.usecase.user.UpdateUserInfoUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UserUseCasesModule {

    @Singleton
    @Provides
    fun provideGetUsersUseCase(userRepository: UserRepository) = GetUsersUseCase(userRepository)

    @Singleton
    @Provides
    fun provideGetUserUseCase(userRepository: UserRepository) = GetUserUseCase(userRepository)

    @Singleton
    @Provides
    fun provideUpdateUserInfoUseCase(
        userRepository: UserRepository,
        userLocalRepository: UserLocalRepository
    ) =
        UpdateUserInfoUseCase(userRepository, userLocalRepository)
}