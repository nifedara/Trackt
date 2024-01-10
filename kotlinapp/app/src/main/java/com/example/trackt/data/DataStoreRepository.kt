package com.example.trackt.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

//with Preference DataStore
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "onboarding_data")
class DataStoreRepository(context: Context){

    private object PreferencesKey{
        val onboardingKey = booleanPreferencesKey(name = "onboarding_completed")
    }

    private val dataStore = context.dataStore

    /**
     * save onboarding state
     */
    suspend fun saveOnboardingState(completed: Boolean){
        dataStore.edit { preferences ->
            preferences[PreferencesKey.onboardingKey] = completed
        }
    }

    /**
     * read onboarding state
     */
    fun readOnboardingState(): Flow<Boolean> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException){
                    emit(emptyPreferences())
                } else{
                    throw exception
                }
            }
            .map{ preferences  ->
                val onboardingState = preferences[PreferencesKey.onboardingKey] ?: false
                onboardingState
            }
    }
}