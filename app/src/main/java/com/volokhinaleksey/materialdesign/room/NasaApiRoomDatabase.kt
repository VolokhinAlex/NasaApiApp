package com.volokhinaleksey.materialdesign.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.volokhinaleksey.materialdesign.model.TasksEntity

@Database(
    entities = [TasksEntity::class],
    version = 1,
    exportSchema = false
)
abstract class NasaApiRoomDatabase : RoomDatabase() {

    abstract val tasksDao: TasksDao

}