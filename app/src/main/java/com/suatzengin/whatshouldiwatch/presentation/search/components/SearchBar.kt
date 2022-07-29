package com.suatzengin.whatshouldiwatch.presentation.search.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.suatzengin.whatshouldiwatch.ui.theme.genreColor


@Composable
fun SearchBar(
    textState: MutableState<String>,
    modifier: Modifier = Modifier,
    searchMovie: (String) -> Unit
) {
    OutlinedTextField(
        modifier = modifier,
        value = textState.value,
        onValueChange = {
            textState.value = it
            searchMovie(it)
        },
        maxLines = 1,
        label = { Text(text = "Search Movie") },
        leadingIcon = {
            Icon(imageVector = Icons.Default.Search, contentDescription = "")
        },
        trailingIcon = {
            IconButton(onClick = {
                if (textState.value.isNotEmpty()) {
                    textState.value = ""
                }
            }) {
                AnimatedVisibility(visible = textState.value.isNotEmpty()) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "")
                }
            }
        },
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = genreColor,
            unfocusedIndicatorColor = Color.Gray,
            cursorColor = Color.DarkGray,
            backgroundColor = Color.Transparent,
            focusedLabelColor = genreColor,
            unfocusedLabelColor = Color.Gray,
        ),
    )

}
