package com.volokhinaleksey.materialdesign.states

import com.volokhinaleksey.materialdesign.model.TasksEntity

sealed class TasksState {
    data class Success(val tasks: List<TasksEntity>) : TasksState()
    data class Error(val message: Throwable) : TasksState()
    object Loading : TasksState()
}