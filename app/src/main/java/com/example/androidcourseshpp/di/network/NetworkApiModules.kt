package com.example.androidcourseshpp.di.network

import com.example.androidcourseshpp.data.source.network.api.auth.AuthAPI
import com.example.androidcourseshpp.data.source.network.api.auth.TokenRefreshAPI
import com.example.androidcourseshpp.data.source.network.api.contacts.ContactsAPI
import com.example.androidcourseshpp.data.source.network.api.user.UserAPI
import com.example.androidcourseshpp.di.MainRetrofit
import com.example.androidcourseshpp.di.TokenRefreshRetrofit
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkApiModules {

    @Provides
    @Singleton
    fun provideAuthAPI(@MainRetrofit retrofit: Retrofit): AuthAPI {
        return retrofit.create(AuthAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideUserAPI(@MainRetrofit retrofit: Retrofit): UserAPI {
        return retrofit.create(UserAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideContactsAPI(@MainRetrofit retrofit: Retrofit) : ContactsAPI {
        return  retrofit.create(ContactsAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideTokenRefreshAPI(@TokenRefreshRetrofit retrofit: Retrofit): TokenRefreshAPI {
        return retrofit.create(TokenRefreshAPI::class.java)
    }
}