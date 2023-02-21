package com.volokhinaleksey.materialdesign.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.volokhinaleksey.materialdesign.model.TasksEntity

/**
 * A local database class for connecting the model and the dao
 */

@Database(
    entities = [TasksEntity::class],
    version = 1,
    exportSchema = false
)
abstract class NasaApiRoomDatabase : RoomDatabase() {

    abstract val tasksDao: TasksDao

}