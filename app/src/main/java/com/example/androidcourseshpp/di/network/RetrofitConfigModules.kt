package com.example.androidcourseshpp.di.network

import com.example.androidcourseshpp.data.network.api.token.TokenRefreshAPI
import com.example.androidcourseshpp.data.network.jwt.JWTManager
import com.example.androidcourseshpp.data.network.jwt.TokenAuthenticator
import com.example.androidcourseshpp.di.MainOkHttpClient
import com.example.androidcourseshpp.di.MainRetrofit
import com.example.androidcourseshpp.di.TokenRefreshOkHttpClient
import com.example.androidcourseshpp.di.TokenRefreshRetrofit
import com.google.gson.Gson
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

const val BASE_URL = "http://178.63.9.114:7777/api/"
@Module
@InstallIn(SingletonComponent::class)
class RetrofitConfigModule {


    @Provides
    @Singleton
    @MainRetrofit
    fun provideMainRetrofit(@MainOkHttpClient client: OkHttpClient, gson: Gson): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    @Singleton
    @MainOkHttpClient
    fun provideMainOkHttpClient(
        jwtManager: JWTManager,
        tokenRefreshAPI: TokenRefreshAPI
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(createLoggingInterceptor())
            .addInterceptor(createAuthorizationInterceptor(jwtManager))
            .authenticator(
                TokenAuthenticator(
                    tokenRefreshAPI,
                    jwtManager
                )
            )
            .build()
    }

    @Provides
    @Singleton
    @TokenRefreshRetrofit
    fun provideTokenRefreshRetrofit(
        @TokenRefreshOkHttpClient client: OkHttpClient,
        gson: Gson
    ): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    @Singleton
    @TokenRefreshOkHttpClient
    fun provideTokenRefreshOkHttpClient(jwtManager: JWTManager) =
        OkHttpClient.Builder().addInterceptor(createAuthenticationInterceptor(jwtManager)).build()


    @Provides
    @Singleton
    fun provideGson() = Gson()

    private fun createLoggingInterceptor() = HttpLoggingInterceptor().setLevel(
        HttpLoggingInterceptor.Level.BODY
    )

    private fun createAuthorizationInterceptor(jwtManager: JWTManager) = Interceptor { chain ->
        val accessToken = jwtManager.getAccessToken()

        val modifiedRequest = chain.request().newBuilder()
        if (accessToken != "") {
            modifiedRequest.addHeader("Authorization", "Bearer $accessToken").build()
        }
        chain.proceed(modifiedRequest.build())

    }

    private fun createAuthenticationInterceptor(jwtManager: JWTManager) = Interceptor { chain ->
        val refreshToken = jwtManager.getRefreshToken()

        val modifiedRequest = chain.request().newBuilder()
        if (refreshToken != null) {
            modifiedRequest.addHeader("RefreshToken", refreshToken).build()
        }
        chain.proceed(modifiedRequest.build())

    }
}



