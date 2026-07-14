package com.example.androidcourseshpp.di.network

import com.example.androidcourseshpp.data.source.local.userdata.DatabaseSyncProvider
import com.example.androidcourseshpp.data.source.local.userdata.GalleryDataProvider
import com.example.androidcourseshpp.data.source.local.userdata.UserDataProvider
import com.example.androidcourseshpp.data.source.network.api.auth.TokenRefreshAPI
import com.example.androidcourseshpp.data.source.network.jwt.JWTManager
import com.example.androidcourseshpp.data.source.network.jwt.TokenAuthenticator
import com.example.androidcourseshpp.di.MainOkHttpClient
import com.example.androidcourseshpp.di.MainRetrofit
import com.example.androidcourseshpp.di.TokenRefreshOkHttpClient
import com.example.androidcourseshpp.di.TokenRefreshRetrofit
import com.example.androidcourseshpp.domain.repository.ContactsLocalRepository
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.ResponseBody.Companion.toResponseBody
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
        @TokenRefreshRetrofit retrofit: Retrofit,
        jwtManager: JWTManager,
        contactsLocalRepository: ContactsLocalRepository,
        userDataProvider: UserDataProvider,
        galleryDataProvider: GalleryDataProvider,
        databaseSyncProvider: DatabaseSyncProvider,
        tokenRefreshAPI: TokenRefreshAPI
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(createResponseInterceptor())
            .addInterceptor(createLoggingInterceptor())
            .addInterceptor(createAuthorizationInterceptor(jwtManager))
            .authenticator(
                TokenAuthenticator(
                    tokenRefreshAPI = tokenRefreshAPI,
                    jwtManager = jwtManager,
                    userDataProvider = userDataProvider,
                    galleryDataProvider = galleryDataProvider,
                    databaseSyncProvider = databaseSyncProvider,
                    contactsLocalRepository = contactsLocalRepository
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

    private fun createResponseInterceptor() = Interceptor { chain ->
        val response = chain.proceed(chain.request())

        val body = response.body
        var bodyString = body.string().trim()
        val contentType = body.contentType()

        if (bodyString.endsWith("]}")) {
            bodyString = "$bodyString}"
        }
        bodyString.replace("nll", "null")
        return@Interceptor response.newBuilder().body(bodyString.toResponseBody(contentType))
            .build()

    }
}



