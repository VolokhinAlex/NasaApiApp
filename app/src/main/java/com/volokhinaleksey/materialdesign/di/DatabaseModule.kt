package com.volokhinaleksey.materialdesign.di

import androidx.room.Room
import com.volokhinaleksey.materialdesign.app.App
import com.volokhinaleksey.materialdesign.room.NasaApiRoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

//@Module
//@InstallIn(SingletonComponent::class)
//class DatabaseModule {
//
//    @Provides
//    fun database(@ApplicationContext app: App): NasaApiRoomDatabase =
//        Room.databaseBuilder(app, NasaApiRoomDatabase::class.java, DB_NAME)
//            .fallbackToDestructiveMigration().build()
//
//    companion object {
//        private const val DB_NAME = "nasaApi.db"
//    }
//
//}