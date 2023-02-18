package com.volokhinaleksey.materialdesign.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DataStoreModule {

    @Provides
    @Singleton
    fun dataStorePreferences(@ApplicationContext appContext: Context): DataStore<Preferences> =
        PreferenceDataStoreFactory.create(corruptionHandler = ReplaceFileCorruptionHandler(
            produceNewData = { emptyPreferences() }
        ),
            migrations = listOf(SharedPreferencesMigration(appContext, NASA_API_PREFERENCES)),
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
            produceFile = { appContext.preferencesDataStoreFile(NASA_API_PREFERENCES) }
        )

    companion object {
        private const val NASA_API_PREFERENCES = "nasa_api_preferences"
    }

}