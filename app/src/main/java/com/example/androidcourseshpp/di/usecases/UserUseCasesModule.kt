package com.example.androidcourseshpp.di.usecases

import com.example.androidcourseshpp.domain.repository.ContactsLocalRepository
import com.example.androidcourseshpp.domain.repository.UserLocalDataRepository
import com.example.androidcourseshpp.domain.repository.UserRepository
import com.example.androidcourseshpp.domain.usecase.user.GetUserAvatarUseCase
import com.example.androidcourseshpp.domain.usecase.user.GetUserAvatarUseCaseImpl
import com.example.androidcourseshpp.domain.usecase.user.GetUserRememberStateUseCase
import com.example.androidcourseshpp.domain.usecase.user.GetUserRememberStateUseCaseImpl
import com.example.androidcourseshpp.domain.usecase.user.GetUserUseCase
import com.example.androidcourseshpp.domain.usecase.user.GetUserUseCaseImpl
import com.example.androidcourseshpp.domain.usecase.user.GetUsersUseCase
import com.example.androidcourseshpp.domain.usecase.user.GetUsersUseCaseImpl
import com.example.androidcourseshpp.domain.usecase.user.UpdateUserInfoUseCase
import com.example.androidcourseshpp.domain.usecase.user.UpdateUserInfoUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UserUseCasesProvideModule {

    @Singleton
    @Provides
    fun provideGetUsersUseCase(
        userRepository: UserRepository,
        contactsLocalRepository: ContactsLocalRepository
    ) = GetUsersUseCaseImpl(
        userRepository = userRepository,
        contactsLocalRepository = contactsLocalRepository
    )

    @Singleton
    @Provides
    fun provideUpdateUserInfoUseCase(
        userRepository: UserRepository,
        userLocalDataRepository: UserLocalDataRepository
    ) = UpdateUserInfoUseCaseImpl(
        userRepository = userRepository,
        userLocalDataRepository = userLocalDataRepository
    )

    @Singleton
    @Provides
    fun provideGetUserUseCase(
        userRepository: UserRepository
    ) = GetUserUseCaseImpl(userRepository)

    @Singleton
    @Provides
    fun provideGetUserAvatarUseCase(userLocalDataRepository: UserLocalDataRepository) =
        GetUserAvatarUseCaseImpl(userLocalDataRepository = userLocalDataRepository)

    @Singleton
    @Provides
    fun provideGetUserRememberStateUseCase(userLocalDataRepository: UserLocalDataRepository) =
        GetUserRememberStateUseCaseImpl(userLocalDataRepository = userLocalDataRepository)
}

@Module
@InstallIn(SingletonComponent::class)
interface UserUseCasesBindModule {

    @Binds
    fun bindGetUsersUseCase(
        getUsersUseCaseImpl: GetUsersUseCaseImpl
    ): GetUsersUseCase

    @Binds
    fun bindGetUserUseCase(
        getUserUseCaseImpl: GetUserUseCaseImpl
    ): GetUserUseCase

    @Binds
    fun bindUpdateUserInfoUseCase(
        updateUserInfoUseCaseImpl: UpdateUserInfoUseCaseImpl
    ): UpdateUserInfoUseCase

    @Binds
    fun bindGetUserAvatarUseCase(
        getUserAvatarUseCaseImpl: GetUserAvatarUseCaseImpl
    ): GetUserAvatarUseCase

    @Binds
    fun bindGetUserRememberStateUseCase(
        getUserRememberStateUseCaseImpl: GetUserRememberStateUseCaseImpl
    ): GetUserRememberStateUseCase
}