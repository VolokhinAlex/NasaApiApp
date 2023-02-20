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

@HiltViewModel
class TasksViewModel @Inject constructor(
    private val tasksRepository: TasksRepository
) : ViewModel() {

    private val searchTasks: MutableLiveData<String> = MutableLiveData("")
    val tasks: LiveData<List<TasksEntity>> = Transformations.switchMap(searchTasks) { query ->
        if (query.isEmpty()) {
            getAllTasks()
        } else {
            tasksRepository.getTasksByQuery(query)
        }
    }

    fun getAllTasks() = tasksRepository.getAllTasks()

    fun delete(tasksEntity: TasksEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            tasksRepository.deleteTask(tasksEntity = tasksEntity)
        }
    }

    fun insert(tasksEntity: TasksEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            tasksRepository.insertTask(tasksEntity = tasksEntity)
        }
    }

    fun update(tasksEntity: TasksEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            tasksRepository.updateTask(tasksEntity = tasksEntity)
        }
    }

    fun searchChangedQuery(newQuery: String) {
        searchTasks.value = newQuery
    }

}