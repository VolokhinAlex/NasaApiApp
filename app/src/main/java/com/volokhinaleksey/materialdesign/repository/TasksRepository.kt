package com.volokhinaleksey.materialdesign.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.volokhinaleksey.materialdesign.model.TasksEntity
import com.volokhinaleksey.materialdesign.room.NasaApiRoomDatabase
import dagger.hilt.components.SingletonComponent
import it.czerwinski.android.hilt.annotations.BoundTo
import javax.inject.Inject

interface TasksRepository {

    fun getAllTasks(): LiveData<List<TasksEntity>>

    suspend fun deleteTask(tasksEntity: TasksEntity)

    suspend fun insertTask(tasksEntity: TasksEntity)

    suspend fun updateTask(tasksEntity: TasksEntity)

    fun getTasksByQuery(text: String): LiveData<List<TasksEntity>>

}

@BoundTo(supertype = TasksRepository::class, component = SingletonComponent::class)
class TasksRepositoryImpl @Inject constructor(
    private val db: NasaApiRoomDatabase
) : TasksRepository {

    override fun getAllTasks(): LiveData<List<TasksEntity>> {
        return db.tasksDao.all()
    }

    @WorkerThread
    override suspend fun deleteTask(tasksEntity: TasksEntity) {
        db.tasksDao.delete(tasksEntity = tasksEntity)
    }

    @WorkerThread
    override suspend fun insertTask(tasksEntity: TasksEntity) {
        db.tasksDao.insert(tasksEntity)
    }

    @WorkerThread
    override suspend fun updateTask(tasksEntity: TasksEntity) {
        db.tasksDao.update(tasksEntity)
    }

    override fun getTasksByQuery(text: String): LiveData<List<TasksEntity>> =
        db.tasksDao.getTasksByQuery(text = text)


}