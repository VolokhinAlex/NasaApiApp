package com.volokhinaleksey.materialdesign.ui.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DropDownMenuWidget(expanded: Boolean, onDismissRequest: () -> Unit) {

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismissRequest
    ) {
        Text(
            "Something",
            fontSize = 18.sp,
            modifier = Modifier
                .padding(10.dp)
                .clickable(onClick = {})
        )
        Text(
            "Something",
            fontSize = 18.sp,
            modifier = Modifier
                .padding(10.dp)
                .clickable(onClick = {})
        )
        Divider()
        Text(
            "Settings",
            fontSize = 18.sp,
            modifier = Modifier
                .padding(10.dp)
                .clickable(onClick = {})
        )
    }

}