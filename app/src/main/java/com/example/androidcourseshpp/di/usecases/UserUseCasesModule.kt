package com.example.androidcourseshpp.di.usecases

import com.example.androidcourseshpp.domain.repository.ContactsLocalRepository
import com.example.androidcourseshpp.domain.repository.UserLocalDataRepository
import com.example.androidcourseshpp.domain.repository.UserRepository
import com.example.androidcourseshpp.domain.usecase.user.GetUserAvatarUseCase
import com.example.androidcourseshpp.domain.usecase.user.GetUserRememberStateUseCase
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
    fun provideGetUsersUseCase(
        userRepository: UserRepository,
        contactsLocalRepository: ContactsLocalRepository
    ) = GetUsersUseCase(
        userRepository = userRepository,
        contactsLocalRepository = contactsLocalRepository
    )

    @Singleton
    @Provides
    fun provideUpdateUserInfoUseCase(
        userRepository: UserRepository,
        userLocalDataRepository: UserLocalDataRepository
    ) = UpdateUserInfoUseCase(
        userRepository = userRepository,
        userLocalDataRepository = userLocalDataRepository
    )

    @Singleton
    @Provides
    fun provideGetUserUseCase(userRepository: UserRepository) = GetUserUseCase(userRepository)

    @Singleton
    @Provides
    fun provideGetUserAvatarUseCase(userLocalDataRepository: UserLocalDataRepository) =
        GetUserAvatarUseCase(userLocalDataRepository = userLocalDataRepository)

    @Singleton
    @Provides
    fun provideGetUserRememberStateUseCase(userLocalDataRepository: UserLocalDataRepository) =
        GetUserRememberStateUseCase(userLocalDataRepository = userLocalDataRepository)
}