package com.volokhinaleksey.materialdesign.ui.widgets

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.volokhinaleksey.materialdesign.model.TasksEntity
import com.volokhinaleksey.materialdesign.ui.Priority

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputDialog(
    isDialogOpen: Boolean,
    onDismissRequest: () -> Unit,
    onClick: (String, String, Priority) -> Unit,
    onClickContent: @Composable () -> Unit,
    task: TasksEntity = TasksEntity(0, "", "", 1),
) {

    var inputTitle by remember { mutableStateOf("") }

    var inputDescription by remember { mutableStateOf("") }

    var selectedItem by remember { mutableStateOf("") }

    if (isDialogOpen) {
        inputTitle = task.title.orEmpty()
        inputDescription = task.description.orEmpty()
        selectedItem = when (task.priority) {
            Priority.Low.priority -> Priority.Low.name
            Priority.Medium.priority -> Priority.Medium.name
            Priority.High.priority -> Priority.High.name
            else -> Priority.Low.name
        }
        AlertDialog(
            onDismissRequest = {
                onDismissRequest()
            }
        ) {
            Surface(
                modifier = Modifier
                    .wrapContentWidth()
                    .wrapContentHeight(),
                shape = MaterialTheme.shapes.large
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState())
                ) {

                    TextField(
                        value = inputTitle, onValueChange = {
                            inputTitle = it
                        }, colors = TextFieldDefaults.textFieldColors(
                            containerColor = Color.Transparent,
                        ),
                        placeholder = { Text(text = "Title") }
                    )

                    TextField(
                        value = inputDescription,
                        onValueChange = {
                            inputDescription = it
                        },
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = Color.Transparent,
                        ),
                        modifier = Modifier.padding(top = 10.dp, bottom = 10.dp),
                        placeholder = { Text(text = "Description") }
                    )

                    ChipsWidget(
                        chips = listOf(
                            Priority.Low.name,
                            Priority.Medium.name,
                            Priority.High.name
                        ), onSelected = {
                            selectedItem = it
                        }, selectedChip = selectedItem
                    )

                    Button(onClick = {
                        onClick(
                            inputTitle,
                            inputDescription,
                            setPriorityByChips(selectedItem)
                        )
                    }) {
                        onClickContent()
                    }
                }
            }
        }
    }
}

fun setPriorityByChips(
    selectedItem: String
): Priority {
    return if (selectedItem.contains(Priority.Low.name)) {
        Priority.Low
    } else if (selectedItem.contains(Priority.Medium.name)) {
        Priority.Medium
    } else {
        Priority.High
    }
}