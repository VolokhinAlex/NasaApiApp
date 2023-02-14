package com.volokhinaleksey.materialdesign.ui.widgets

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.core.content.ContextCompat
import com.volokhinaleksey.materialdesign.R

@Composable
fun SearchWidget(
    modifier: Modifier = Modifier,
    text: String,
    label: String,
    onValueChange: (String) -> Unit,
) {
    SearchField(modifier = modifier, text = text, onValueChange = onValueChange, label = label)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchField(
    modifier: Modifier = Modifier,
    text: String = "",
    onValueChange: (String) -> Unit,
    label: String = "",
) {
    val context = LocalContext.current
    OutlinedTextField(
        value = text,
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
                            Uri.parse("https://en.wikipedia.org/wiki/${text}")
                    }, null)

                }
            )
        },
        modifier = modifier
    )
}


@Composable
fun rememberSearchState(
    query: String = "",
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

class SearchState(query: String, searching: Boolean, focused: Boolean) {
    var query by mutableStateOf(query)
    var focused by mutableStateOf(focused)
    var searching by mutableStateOf(searching)
}