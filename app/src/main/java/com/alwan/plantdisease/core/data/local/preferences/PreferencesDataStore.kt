package com.alwan.plantdisease.core.data.local.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "preferences")

class PreferencesDataStore(private val context: Context) {

    suspend fun saveBaseUrl(baseUrl: String) {
        context.dataStore.edit { preferences ->
            preferences[BASE_URL_KEY] = baseUrl
        }
    }

    suspend fun savePlantCategory(category: String) {
        context.dataStore.edit { preferences ->
            preferences[PLANT_CATEGORY_KEY] = category
        }
    }

    val baseUrlFlow: Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[BASE_URL_KEY] ?: "http://192.168.0.1:5000/"
        }.distinctUntilChanged()

    val plantCategoryFlow: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[PLANT_CATEGORY_KEY] ?: "potato"
    }.distinctUntilChanged()

    companion object {
        val BASE_URL_KEY = stringPreferencesKey("base_url")
        val PLANT_CATEGORY_KEY = stringPreferencesKey("plant_category")
    }
}