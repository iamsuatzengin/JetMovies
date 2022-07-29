package com.suatzengin.whatshouldiwatch.presentation.genres

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.suatzengin.whatshouldiwatch.data.remote.genre.Genre
import com.suatzengin.whatshouldiwatch.presentation.navigation.Screen
import com.suatzengin.whatshouldiwatch.ui.theme.genreBgColor
import com.suatzengin.whatshouldiwatch.ui.theme.genreColor


@Composable
fun GenresScreen(
    viewModel: GenresViewModel = hiltViewModel(),
    navController: NavController
) {
    val genres = viewModel.stateGenres.value
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Türler") },
                backgroundColor = Color.Transparent,
                elevation = 0.dp
            )
        },
        modifier = Modifier.statusBarsPadding()
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.padding(it)
        ) {
            items(genres) { genre ->
                GenresItem(genre = genre, onClick = {
                    navController.navigate(Screen.MovieWithGenres.route + "/${genre.id}/${genre.name}")
                })
            }
        }
    }
}

@Composable
fun GenresItem(genre: Genre, onClick: (Genre) -> Unit) {

    Card(
        elevation = 2.dp,
        shape = RoundedCornerShape(50),
        backgroundColor = genreBgColor,
        contentColor = genreColor,
        modifier = Modifier
            .padding(8.dp)
            .clickable { onClick(genre) }
    ) {
        Text(
            text = genre.name,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(vertical = 8.dp),
            fontSize = 18.sp
        )
    }
}