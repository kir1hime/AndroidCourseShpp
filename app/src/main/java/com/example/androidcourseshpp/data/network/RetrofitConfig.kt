package com.example.androidcourseshpp.data.network

import com.example.androidcourseshpp.di.MainRetrofit
import com.google.gson.Gson
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RetrofitConfig @Inject constructor(@MainRetrofit val retrofit: Retrofit, val gson: Gson)