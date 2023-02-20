package com.volokhinaleksey.materialdesign.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.volokhinaleksey.materialdesign.model.TasksEntity

@Database(
    entities = [TasksEntity::class],
    version = 1,
    exportSchema = false
)
abstract class NasaApiRoomDatabase : RoomDatabase() {

    abstract val tasksDao: TasksDao

    companion object {
        private const val DB_NAME = "nasaApi.db"
        private var instance: NasaApiRoomDatabase? = null

        fun getInstance() = instance
            ?: throw RuntimeException("Database has not been created. Please call create(context)")

        fun createDatabase(context: Context?) {
            if (instance == null) {
                instance =
                    Room.databaseBuilder(
                        context!!,
                        NasaApiRoomDatabase::class.java,
                        DB_NAME
                    ).build()
            }
        }
    }

}