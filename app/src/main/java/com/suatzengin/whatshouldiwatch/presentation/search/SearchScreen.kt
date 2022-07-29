package com.suatzengin.whatshouldiwatch.presentation.search


import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.suatzengin.whatshouldiwatch.presentation.search.components.SearchBar
import com.suatzengin.whatshouldiwatch.presentation.search.components.SearchedList

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel(),
    navController: NavController
) {
    val movies = viewModel.searchedMovies.collectAsLazyPagingItems()
    val text = viewModel.searchQuery
    val keyboardController = LocalSoftwareKeyboardController.current
    val manager = LocalFocusManager.current

    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .pointerInput(Unit){
                detectTapGestures(onTap = {
                    keyboardController?.hide()
                    manager.clearFocus()
                })
            }
    ) {

        Column {
            SearchBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp), textState = text
            ) {
                viewModel.searchMovie(it)
            }
            Divider()

            SearchedList(movies = movies, navController = navController)
        }
    }
}








