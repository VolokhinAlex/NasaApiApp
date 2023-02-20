package com.volokhinaleksey.materialdesign.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import com.volokhinaleksey.materialdesign.R
import com.volokhinaleksey.materialdesign.model.TasksEntity
import com.volokhinaleksey.materialdesign.ui.Priority
import com.volokhinaleksey.materialdesign.ui.widgets.ExpandableCard
import com.volokhinaleksey.materialdesign.ui.widgets.InputDialog
import com.volokhinaleksey.materialdesign.viewmodels.TasksViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val DESCRIPTION_MAX_LINES = 4

@Composable
fun TasksScreen(tasksViewModel: TasksViewModel = hiltViewModel()) {
    var openDialog by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        tasksViewModel.tasks.observeAsState().value?.let { state ->
            RenderData(state, tasksViewModel)
        }

        FloatingActionButton(
            onClick = { openDialog = true },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 15.dp, bottom = 15.dp),
            elevation = FloatingActionButtonDefaults.elevation(5.dp)
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = Icons.Default.Add.name)
        }
    }

    InputDialog(
        isDialogOpen = openDialog,
        onDismissRequest = {
            openDialog = false
        }, onClick = { title, description, priority ->
            if (isDataNotEmpty(title, description)) {
                tasksViewModel.insert(
                    TasksEntity(
                        0, title, description, priority.priority
                    )
                )
                openDialog = false
            }
        }, onClickContent = {
            Text(text = stringResource(R.string.create_task_btn))
        }
    )
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun RenderData(state: List<TasksEntity>, tasksViewModel: TasksViewModel) {

    var openDialog by remember { mutableStateOf(false) }
    var rememberItem by remember {
        mutableStateOf(TasksEntity(0, null, null, null))
    }

    var query by remember { mutableStateOf("") }
    var searching by remember { mutableStateOf(false) }

    LazyColumn {
        stickyHeader {
            Box {
                SearchBar(
                    query = query,
                    onQueryChange = {
                        tasksViewModel.viewModelScope.launch {
                            query = it
                            searching = true
                            delay(500)
                            tasksViewModel.searchChangedQuery(it)
                            searching = false
                        }
                    },
                    onSearch = {},
                    active = false,
                    onActiveChange = {},
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = Icons.Default.Search.name
                        )
                    },
                    trailingIcon = {
                        when {
                            searching -> {
                                CircularProgressIndicator(
                                    modifier = Modifier
                                        .padding(horizontal = 6.dp)
                                        .size(36.dp)
                                )
                            }
                            query.isNotEmpty() -> {
                                IconButton(onClick = {
                                    query = ""
                                }) {
                                    Icon(
                                        imageVector = Icons.Filled.Close,
                                        contentDescription = Icons.Filled.Close.name
                                    )
                                }
                            }
                        }
                    },
                    placeholder = { Text(text = stringResource(R.string.placeholder_search_task)) },
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(start = 10.dp, end = 10.dp),
                    content = {}
                )
            }
        }

        itemsIndexed(state) { _, item ->
            val priority = when (item.priority) {
                Priority.Low.priority -> Priority.Low
                Priority.Medium.priority -> Priority.Medium
                Priority.High.priority -> Priority.High
                else -> Priority.Low
            }
            ExpandableCard(
                content = {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = item.title.orEmpty())
                        Column {
                            IconButton(
                                onClick = {
                                    rememberItem = TasksEntity(
                                        id = item.id,
                                        title = item.title.orEmpty(),
                                        description = item.description.orEmpty(),
                                        priority = item.priority
                                    )
                                    openDialog = true
                                }
                            ) {
                                Icon(
                                    Icons.Filled.Edit,
                                    contentDescription = Icons.Filled.Edit.name,
                                    tint = MaterialTheme.colorScheme.tertiary
                                )
                            }
                            IconButton(
                                onClick = {
                                    tasksViewModel.delete(item)
                                }
                            ) {
                                Icon(
                                    Icons.Filled.Delete,
                                    contentDescription = Icons.Filled.Delete.name,
                                    tint = Color.Red
                                )
                            }
                        }
                    }
                },
                expandContent = {
                    Row(modifier = Modifier.fillMaxSize()) {
                        Text(
                            text = item.description.orEmpty(),
                            maxLines = DESCRIPTION_MAX_LINES,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                },
                colors = CardDefaults.cardColors(
                    containerColor = when (priority) {
                        Priority.Medium -> {
                            MaterialTheme.colorScheme.inverseSurface
                        }
                        Priority.High -> {
                            MaterialTheme.colorScheme.primary
                        }
                        else -> {
                            MaterialTheme.colorScheme.surfaceVariant
                        }
                    }
                )
            )
        }
    }
    InputDialog(
        isDialogOpen = openDialog,
        onDismissRequest = { openDialog = false },
        onClick = { title, description, itemPriority ->
            if (isDataNotEmpty(title, description)) {
                tasksViewModel.update(
                    TasksEntity(
                        id = rememberItem.id,
                        title = title,
                        description = description,
                        priority = itemPriority.priority
                    )
                )
                openDialog = false
            }
        },
        onClickContent = {
            Text(text = stringResource(R.string.edit_task_btn))
        },
        task = rememberItem
    )
}


private fun isDataNotEmpty(title: String, description: String): Boolean {
    return title.isNotEmpty() && description.isNotEmpty()
}
