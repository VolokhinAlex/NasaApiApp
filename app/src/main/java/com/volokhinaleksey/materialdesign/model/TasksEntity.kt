package com.volokhinaleksey.materialdesign.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Date class database model for the task list
 *
 *  @param id - Unique identifier for each task (PrimaryKey)
 *  @param title - Task title
 *  @param description - task description
 *  @param priority - Task priority. It can be from 1 to 3. Where 1 is the lowest and 3 is the highest.
 *  For more information, see the class [Priority]
 *
 */

@Entity(tableName = "tasks")
data class TasksEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val title: String?,
    val description: String?,
    val priority: Int?
)