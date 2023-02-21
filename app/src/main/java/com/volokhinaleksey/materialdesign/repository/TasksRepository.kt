package com.volokhinaleksey.materialdesign.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.volokhinaleksey.materialdesign.model.TasksEntity
import com.volokhinaleksey.materialdesign.room.NasaApiRoomDatabase
import dagger.hilt.components.SingletonComponent
import it.czerwinski.android.hilt.annotations.BoundTo
import javax.inject.Inject

interface TasksRepository {

    /**
     * Method for getting a list of all tasks from the local database
     */

    fun getAllTasks(): LiveData<List<TasksEntity>>

    /**
     * Method for deleting a task from the local database
     */

    suspend fun deleteTask(tasksEntity: TasksEntity)

    /**
     * Method for adding a task to the local database
     */

    suspend fun insertTask(tasksEntity: TasksEntity)

    /**
     * Method for updating the task in the local database
     */

    suspend fun updateTask(tasksEntity: TasksEntity)

    /**
     * Method for getting a list of tasks by query from the local database
     */

    fun getTasksByQuery(text: String): LiveData<List<TasksEntity>>

}

@BoundTo(supertype = TasksRepository::class, component = SingletonComponent::class)
class TasksRepositoryImpl @Inject constructor(
    private val db: NasaApiRoomDatabase
) : TasksRepository {

    /**
     * Method for getting a list of all tasks from the local database
     */

    override fun getAllTasks(): LiveData<List<TasksEntity>> {
        return db.tasksDao.all()
    }

    /**
     * Method for deleting a task from the local database
     * @param tasksEntity - The task to be deleted from the database
     */

    @WorkerThread
    override suspend fun deleteTask(tasksEntity: TasksEntity) {
        db.tasksDao.delete(tasksEntity = tasksEntity)
    }

    /**
     * Method for adding a task to the local database
     * @param tasksEntity - The task to be added to the database
     */

    @WorkerThread
    override suspend fun insertTask(tasksEntity: TasksEntity) {
        db.tasksDao.insert(tasksEntity)
    }

    /**
     * Method for updating the task in the local database
     * @param tasksEntity - The task to be updated in the database
     */

    @WorkerThread
    override suspend fun updateTask(tasksEntity: TasksEntity) {
        db.tasksDao.update(tasksEntity)
    }

    /**
     * Method for getting a list of tasks by query from the local database
     * @param text - A request to find a list of all tasks and return it
     */

    override fun getTasksByQuery(text: String): LiveData<List<TasksEntity>> =
        db.tasksDao.getTasksByQuery(text = text)


}