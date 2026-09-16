package com.example.androidcourseshpp.data.source.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.androidcourseshpp.data.source.local.database.dao.ContactsDao
import com.example.androidcourseshpp.data.source.local.database.dbentity.ContactDbEntity
import com.example.androidcourseshpp.data.source.local.database.utils.SyncStateConverter

@Database(
    version = 1,
    entities = [ContactDbEntity::class]
)
@TypeConverters(SyncStateConverter::class)
abstract class AppDatabase() : RoomDatabase() {

    abstract fun getContactsDao(): ContactsDao
}