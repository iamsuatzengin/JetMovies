package com.suatzengin.whatshouldiwatch.presentation.search.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.coil.CoilImage
import com.suatzengin.whatshouldiwatch.common.Constants
import com.suatzengin.whatshouldiwatch.data.remote.movie.Movie
import com.suatzengin.whatshouldiwatch.presentation.navigation.Screen


@Composable
fun SearchedList(
    movies: LazyPagingItems<Movie>,
    navController: NavController
) {

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(movies) { item ->
            item?.let { movie ->
                Column {
                    SearchedItem(movie = movie) { navigatedItem ->
                        navController.navigate(Screen.MovieDetail.route + "/${navigatedItem.id}")
                    }
                }
            }
        }
        movies.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    //when first time response page is loading
                    item {
                        Text(text = "Henüz Arama Yapmadınız")
                    }
                }
                loadState.append is LoadState.Loading -> {
                    item {
                        LinearProgressIndicator(
                            color = Color.Red,
                            modifier = Modifier
                                .fillMaxWidth()
                                .statusBarsPadding(),
                        )
                    }
                }
                loadState.append is LoadState.Error -> {
                    item { Text(text = "Hata") }
                }

            }
        }
    }
}

@Composable
fun SearchedItem(movie: Movie, onClick: (Movie) -> Unit) {
    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = 4.dp,
        modifier = Modifier
            .height(220.dp)
            .padding(12.dp)
            .clickable {
                onClick(movie)
            }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()

        ) {
            CoilImage(
                imageModel = Constants.IMAGE_URL + movie.backdropPath,
                contentScale = ContentScale.Crop,
                shimmerParams = ShimmerParams(
                    baseColor = MaterialTheme.colors.background,
                    highlightColor = Color.LightGray.copy(alpha = 0.6f),
                    durationMillis = 350, dropOff = 0.65f, tilt = 20f
                ),
                circularReveal = CircularReveal(duration = 350),
                failure = { Text(text = "Image request failed!") },
                modifier = Modifier
                    .height(250.dp)
                    .shadow(elevation = 8.dp, shape = RoundedCornerShape(12.dp)),
            )
            Text(
                movie.originalTitle,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent, Color.LightGray
                            )
                        ), shape = RoundedCornerShape(8.dp)
                    ),
                color = Color.White,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.h6
            )
            Text(
                text = "${movie.voteAverage}",
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
                    .background(color = Color(0xFF5B5E6D), shape = RoundedCornerShape(8.dp))
                    .padding(4.dp),
                color = Color.White
            )
        }
    }
}
