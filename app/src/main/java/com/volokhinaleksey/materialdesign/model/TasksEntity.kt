package com.volokhinaleksey.materialdesign.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class TasksEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val title: String?,
    val description: String?,
    val priority: Int?
)