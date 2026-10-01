package com.example.androidcourseshpp.data.common

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.first

open class BasePreferencesProvider(private val preferences: DataStore<Preferences>) {
    protected suspend fun <T> savePreference(key: Preferences.Key<T>, value: T) {
        preferences.edit { preferences ->
            preferences[key] = value
        }
    }

    protected suspend fun <T> getPreference(key: Preferences.Key<T>): T? {
        return preferences.data.first()[key]
    }

    protected suspend fun <T> clearPreference(key: Preferences.Key<T>) {
        preferences.edit { preferences ->
            preferences.remove(key)
        }
    }
}