package com.volokhinaleksey.materialdesign.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.volokhinaleksey.materialdesign.model.TasksEntity


@Dao
interface TasksDao {

    @Query("SELECT * FROM tasks")
    fun all(): LiveData<List<TasksEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(tasksEntity: TasksEntity)

    @Update
    fun update(tasksEntity: TasksEntity)

    @Delete
    fun delete(tasksEntity: TasksEntity)

    @Query(
        "SELECT * FROM tasks WHERE LOWER(title) LIKE '%' || :text || '%' " +
                "OR LOWER(description) LIKE '%' || :text || '%'"
    )
    fun getTasksByQuery(text: String): LiveData<List<TasksEntity>>

}