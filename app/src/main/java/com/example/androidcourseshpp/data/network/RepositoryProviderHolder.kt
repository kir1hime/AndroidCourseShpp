package com.example.androidcourseshpp.data.network

import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "http://178.63.9.114:7777/api/"

object RepositoryProviderHolder {

    val repositoryProvider: RepositoriesProvider by lazy<RepositoriesProvider> {
        val gson = Gson()
        val config = RetrofitConfig(retrofit = createRetrofit(gson), gson = gson)

        RetrofitRepositoryProvider(config)
    }

    private fun createRetrofit(gson: Gson): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .client(createOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    private fun createOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
           .addInterceptor(createLoggingInterceptor())
            .build()
    }

    private fun createLoggingInterceptor() = HttpLoggingInterceptor().setLevel(
        HttpLoggingInterceptor.Level.BODY
    )

}