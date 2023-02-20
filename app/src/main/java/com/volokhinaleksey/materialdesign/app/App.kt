package com.volokhinaleksey.materialdesign.app

import android.app.Application
import com.volokhinaleksey.materialdesign.BuildConfig
import com.volokhinaleksey.materialdesign.room.NasaApiRoomDatabase
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        app = this
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        NasaApiRoomDatabase.createDatabase(this)
    }

    companion object {
        lateinit var app: App
    }

}