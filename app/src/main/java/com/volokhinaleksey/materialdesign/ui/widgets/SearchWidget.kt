package com.volokhinaleksey.materialdesign.ui.widgets

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.core.content.ContextCompat
import com.volokhinaleksey.materialdesign.R

@Composable
fun SearchWidget(
    modifier: Modifier = Modifier,
    query: TextFieldValue,
    label: String,
    onValueChange: (String) -> Unit,
) {
    SearchField(modifier = modifier, query = query, onValueChange = onValueChange, label = label)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchField(
    modifier: Modifier = Modifier,
    query: TextFieldValue,
    onValueChange: (String) -> Unit,
    label: String = "",
) {
    val context = LocalContext.current
    OutlinedTextField(
        value = query.text,
        onValueChange = onValueChange,
        label = { Text(text = label) },
        trailingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.wikipedia),
                tint = MaterialTheme.colorScheme.outline,
                contentDescription = "Back button",
                modifier = Modifier.clickable {
                    ContextCompat.startActivity(context, Intent(Intent.ACTION_VIEW).apply {
                        data =
                            Uri.parse("https://en.wikipedia.org/wiki/${query}")
                    }, null)

                }
            )
        },
        modifier = modifier
    )
}


@Composable
fun rememberSearchState(
    query: TextFieldValue = TextFieldValue(""),
    focused: Boolean = false,
    searching: Boolean = false,
): SearchState {
    return remember {
        SearchState(
            query = query,
            focused = focused,
            searching = searching
        )
    }
}

class SearchState(query: TextFieldValue, searching: Boolean, focused: Boolean) {
    var query by mutableStateOf(query)
    var focused by mutableStateOf(focused)
    var searching by mutableStateOf(searching)
}