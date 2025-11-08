package com.example.androidcourseshpp.di

import com.example.androidcourseshpp.data.network.BASE_URL
import com.example.androidcourseshpp.data.network.RetrofitServicesProvider
import com.example.androidcourseshpp.data.network.ServicesProvider
import com.example.androidcourseshpp.data.network.jwt.JWTManager
import com.example.androidcourseshpp.data.network.service.auth.AuthService
import com.example.androidcourseshpp.data.network.service.auth.AuthServiceImpl
import com.example.androidcourseshpp.data.network.service.user.UserService
import com.example.androidcourseshpp.data.network.service.user.UserServiceImpl
import com.google.gson.Gson
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RetrofitServicesModules {

    @Binds
    fun provideAuthService(authServiceImpl: AuthServiceImpl): AuthService

    @Binds
    fun provideUserService(userServiceImpl: UserServiceImpl): UserService

    @Binds
    fun provideServiceProvider(retrofitServicesProvider: RetrofitServicesProvider): ServicesProvider
}

@Module
@InstallIn(SingletonComponent::class)
class RetrofitConfigModule {


    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient, gson: Gson): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    @Singleton
    fun provideGson() = Gson()

    @Provides
    @Singleton
    fun provideOkHttpClient(jwtManager: JWTManager): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(createLoggingInterceptor())
            .addInterceptor(createAuthorizationInterceptor(jwtManager))
            .build()
    }

    private fun createLoggingInterceptor() = HttpLoggingInterceptor().setLevel(
        HttpLoggingInterceptor.Level.BODY
    )

    private fun createAuthorizationInterceptor(jwtManager: JWTManager) = Interceptor { chain ->
        val accessToken = jwtManager.getAccessToken()

        val modifiedRequest = chain.request().newBuilder()
        if (accessToken != null) {
            modifiedRequest.addHeader("Authorization", "Bearer $accessToken").build()
        }
        chain.proceed(modifiedRequest.build())

    }
}

