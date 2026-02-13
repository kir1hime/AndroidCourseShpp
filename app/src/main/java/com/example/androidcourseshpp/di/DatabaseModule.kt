package com.example.androidcourseshpp.di

import android.content.Context
import androidx.room.Room
import com.example.androidcourseshpp.data.source.local.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext

import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

const val DATABASE_NAME = "app_database.db"

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDataBase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideContactsDao(appDatabase: AppDatabase) = appDatabase.getContactsDao()

}
