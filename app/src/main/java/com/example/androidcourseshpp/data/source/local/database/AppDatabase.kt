package com.example.androidcourseshpp.data.source.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.androidcourseshpp.data.source.local.database.dao.ContactsDao
import com.example.androidcourseshpp.data.source.local.database.dbentity.ContactDbEntity

@Database(
    version = 1,
    entities = [ContactDbEntity::class]
)
abstract class AppDatabase() : RoomDatabase() {

    abstract fun getContactsDao(): ContactsDao
}