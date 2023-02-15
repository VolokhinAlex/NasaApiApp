package com.volokhinaleksey.materialdesign.ui.screens

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.volokhinaleksey.materialdesign.ui.widgets.SearchWidget
import com.volokhinaleksey.materialdesign.ui.widgets.rememberSearchState

@Composable
fun SearchScreen() {
    val searchState = rememberSearchState()
    SearchWidget(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp, top = 10.dp, bottom = 10.dp),
        query = searchState.query,
        label = "Search in Wiki"
    ) { searchState.query = TextFieldValue(it) }

}