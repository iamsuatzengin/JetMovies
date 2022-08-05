package com.suatzengin.whatshouldiwatch.presentation.watch_list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.suatzengin.whatshouldiwatch.R
import com.suatzengin.whatshouldiwatch.presentation.watch_list.components.WatchListItem
import kotlinx.coroutines.launch


@Composable
fun WatchListScreen(
    viewModel: WatchListViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    Scaffold(
        modifier = Modifier
            .statusBarsPadding()
            .imePadding(),
        topBar = {
            TopAppBar(
                title = { Text(text = "Watch List") },
                backgroundColor = Color.Transparent,
                elevation = 0.dp
            )
        },
        scaffoldState = scaffoldState
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            LazyColumn(modifier = Modifier.padding(it)) {
                items(items = state.list) { item ->
                    WatchListItem(movie = item) {
                        viewModel.onEvent(WatchListEvent.DeleteMovie(item))
                        scope.launch {
                            val result = scaffoldState.snackbarHostState.showSnackbar(
                                message = "Movie deleted",
                                actionLabel = "Undo"
                            )
                            if (result == SnackbarResult.ActionPerformed) {
                                viewModel.onEvent(WatchListEvent.RestoreMovie)
                            }
                        }
                    }
                }
            }
            if (state.isEmpty) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_no_data),
                        contentDescription = ""
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "There is no movie in your Watch List")
                }
            }
            if (state.isLoading) {
                CircularProgressIndicator()
            }
            if (state.error.isNotBlank()) {
                Text(text = "error: ${state.error}")
            }
        }
    }
}
