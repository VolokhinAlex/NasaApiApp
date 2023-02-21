package com.volokhinaleksey.materialdesign.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.volokhinaleksey.materialdesign.model.TasksEntity
import com.volokhinaleksey.materialdesign.repository.TasksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * A class with the business logic of the Tasks Screen.
 * The class allows you to work with getting/saving/deleting/changing data to a database
 *
 * @param tasksRepository - Repository for getting data
 */
@HiltViewModel
class TasksViewModel @Inject constructor(
    private val tasksRepository: TasksRepository
) : ViewModel() {

    /**
     * searchTasks object for monitoring user requests in search
     */

    private val searchTasks: MutableLiveData<String> = MutableLiveData("")

    val tasks: LiveData<List<TasksEntity>> = Transformations.switchMap(searchTasks) { query ->
        if (query.isEmpty()) {
            getAllTasks()
        } else {
            tasksRepository.getTasksByQuery(query)
        }
    }

    /**
     * Method for getting all tasks from the repository
     */

    private fun getAllTasks() = tasksRepository.getAllTasks()

    /**
     * Method for deleting a task from the database
     * @param tasksEntity - deleted model.
     */

    fun delete(tasksEntity: TasksEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            tasksRepository.deleteTask(tasksEntity = tasksEntity)
        }
    }

    /**
     * Method for inserting a task to the database
     */

    fun insert(tasksEntity: TasksEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            tasksRepository.insertTask(tasksEntity = tasksEntity)
        }
    }

    /**
     * Method for updating a task in the database
     * @param tasksEntity - updated model.
     */

    fun update(tasksEntity: TasksEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            tasksRepository.updateTask(tasksEntity = tasksEntity)
        }
    }

    fun searchChangedQuery(newQuery: String) {
        searchTasks.value = newQuery
    }

}