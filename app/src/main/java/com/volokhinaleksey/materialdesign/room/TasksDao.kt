package com.volokhinaleksey.materialdesign.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.volokhinaleksey.materialdesign.model.TasksEntity

/**
 * Interface for interacting with a local database
 */

@Dao
interface TasksDao {

    /**
     * Method for getting all the tasks from database
     */

    @Query("SELECT * FROM tasks")
    fun all(): LiveData<List<TasksEntity>>

    /**
     * Method for inserting task into the database
     * @param tasksEntity - A model for insert to the database
     */

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(tasksEntity: TasksEntity)

    /**
     * Method for updating task in the database
     * @param tasksEntity - A model for update in the database
     */

    @Update
    fun update(tasksEntity: TasksEntity)

    /**
     * Method for deleting task from the database
     * @param tasksEntity - A model for delete from the database
     */

    @Delete
    fun delete(tasksEntity: TasksEntity)

    /**
     * Method for getting all tasks by something query.
     * The entered query will be checked in the header column and the task description column
     * @param text - Something string query.
     */

    @Query(
        "SELECT * FROM tasks WHERE LOWER(title) LIKE '%' || :text || '%' " +
                "OR LOWER(description) LIKE '%' || :text || '%'"
    )
    fun getTasksByQuery(text: String): LiveData<List<TasksEntity>>

}