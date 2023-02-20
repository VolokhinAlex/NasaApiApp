package com.volokhinaleksey.materialdesign.di

import android.content.Context
import androidx.room.Room
import com.volokhinaleksey.materialdesign.room.NasaApiRoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * A module for providing a dependency for a database
 */

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    /**
     * The method provides a database dependency
     * @param appContext - Application context
     */

    @Provides
    @Singleton
    fun database(@ApplicationContext appContext: Context): NasaApiRoomDatabase =
        Room.databaseBuilder(appContext, NasaApiRoomDatabase::class.java, DB_NAME)
            .fallbackToDestructiveMigration().build()

    companion object {
        private const val DB_NAME = "nasaApi.db"
    }

}