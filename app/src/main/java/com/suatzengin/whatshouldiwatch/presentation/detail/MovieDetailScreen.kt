package com.suatzengin.whatshouldiwatch.presentation.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.BookmarkAdd
import androidx.compose.material.icons.filled.StarRate
import androidx.compose.material.icons.outlined.BookmarkAdd
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.coil.CoilImage
import com.suatzengin.whatshouldiwatch.common.Constants
import com.suatzengin.whatshouldiwatch.data.local.entities.MovieEntity
import com.suatzengin.whatshouldiwatch.presentation.detail.components.MovieCast
import com.suatzengin.whatshouldiwatch.presentation.detail.components.MovieDescription
import com.suatzengin.whatshouldiwatch.presentation.detail.components.MovieGenres
import com.suatzengin.whatshouldiwatch.presentation.detail.components.SimilarMovies
import com.suatzengin.whatshouldiwatch.presentation.navigation.Screen
import com.suatzengin.whatshouldiwatch.ui.theme.ratingStarColor
import kotlin.time.Duration.Companion.minutes

@Composable
fun MovieDetailScreen(
    viewModel: MovieDetailViewModel = hiltViewModel(),
    navController: NavController,

    ) {
    val state = viewModel.state.value


    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.surface)
            .verticalScroll(rememberScrollState())
            .navigationBarsPadding()
    ) {
        state.movie?.let { movie ->

            LaunchedEffect(key1 = Unit){
                viewModel.onEvent(event = MoviesEvent.IsBookmarked(movie.id))
            }

            val genres = remember {
                mutableStateOf(movie.genres)
            }

            val (backdrop, poster, arrowBack, save, originalTitle) = createRefs()
            val (rateScore, genresFlowRow, row, description, similar, cast) = createRefs()


            CoilImage(
                imageModel = Constants.IMAGE_URL + movie.backdropPath,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(350.dp)
                    .clip(RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp))
                    .constrainAs(backdrop) {},
                contentScale = ContentScale.FillHeight,
                shimmerParams = ShimmerParams(
                    baseColor = MaterialTheme.colors.background,
                    highlightColor = Color.LightGray.copy(alpha = 0.6f),
                    durationMillis = 350, dropOff = 0.65f, tilt = 20f
                ),
                circularReveal = CircularReveal(duration = 350),
                failure = { Text(text = "Resim yüklenemedi!") },
            )

            CoilImage(
                imageModel = Constants.IMAGE_URL + movie.posterPath,
                modifier = Modifier
                    .width(150.dp)
                    .height(180.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .constrainAs(poster) {
                        centerAround(backdrop.bottom)
                        linkTo(start = parent.start, end = parent.end)
                    },
                circularReveal = CircularReveal(duration = 350),
            )
            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier.constrainAs(arrowBack) {
                    linkTo(start = parent.start, end = poster.start)
                    linkTo(top = backdrop.bottom, bottom = poster.bottom)
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Arrow Back Icon"
                )
            }

            IconButton(
                onClick = {
                    val addingMovie = MovieEntity(
                        movieId = movie.id,
                        title = movie.title,
                        originalTitle = movie.originalTitle,
                        posterPath = movie.posterPath,
                        voteAverage = movie.voteAverage,
                        runtime = movie.runtime,
                        overview = movie.overview
                    )
                    if (viewModel.isBookmarked.value) {
                        viewModel.onEvent(MoviesEvent.DeleteMovie(movie = addingMovie))

                    } else {
                        viewModel.onEvent(MoviesEvent.BookmarkMovie(movie = addingMovie))
                    }

                },
                modifier = Modifier.constrainAs(save) {
                    linkTo(start = poster.end, end = parent.end)
                    linkTo(top = backdrop.bottom, bottom = poster.bottom)
                }
            ) {
                Icon(
                    imageVector =
                    if (viewModel.isBookmarked.value) Icons.Filled.BookmarkAdd
                    else Icons.Outlined.BookmarkAdd,
                    contentDescription = "Add Watchlist Icon",

                )
            }
            Text(
                text = movie.originalTitle,
                modifier = Modifier.constrainAs(originalTitle) {
                    linkTo(start = parent.start, end = parent.end)
                    top.linkTo(poster.bottom, margin = 4.dp)
                }
            )
            Row(
                modifier = Modifier.constrainAs(rateScore) {
                    top.linkTo(originalTitle.bottom, margin = 4.dp)
                    linkTo(start = parent.start, end = parent.end)
                },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.StarRate, contentDescription = "",
                    tint = ratingStarColor
                )
                Text(text = "${movie.voteAverage} / 10 IMDb", color = Color.LightGray)
            }

            MovieGenres(
                genres = genres.value,
                modifier = Modifier.constrainAs(genresFlowRow) {
                    linkTo(start = parent.start, end = parent.end)
                    top.linkTo(rateScore.bottom, margin = 4.dp)
                }, navController = navController
            )
            Row(
                modifier = Modifier
                    .constrainAs(row) {
                        top.linkTo(genresFlowRow.bottom, margin = 8.dp)
                    }
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(text = "Time", color = Color.LightGray, fontSize = 16.sp)
                    Text(text = "${movie.runtime?.minutes}")
                }
                Column {
                    Text(text = "Language", color = Color.LightGray, fontSize = 16.sp)
                    Text(text = movie.spokenLanguages[0].englishName)
                }
                Column {
                    Text(text = "Status", color = Color.LightGray, fontSize = 16.sp)
                    Text(text = movie.status)
                }

            }
            MovieDescription(
                description = "${movie.overview}",
                modifier = Modifier.constrainAs(description) {
                    top.linkTo(row.bottom, margin = 8.dp)
                    linkTo(start = parent.start, end = parent.end)
                }
            )
            MovieCast(
                cast = movie.credit.cast,
                modifier = Modifier.constrainAs(cast) {
                    top.linkTo(description.bottom, margin = 8.dp)
                    linkTo(start = parent.start, end = parent.end)
                }
            )
            SimilarMovies(
                similar = movie.similar.results,
                modifier = Modifier.constrainAs(similar) {
                    top.linkTo(cast.bottom, margin = 8.dp)
                    linkTo(start = parent.start, end = parent.end)
                }
            ) { similarMovie ->
                navController.navigate(Screen.MovieDetail.route + "/${similarMovie.id}")
            }

        }
        val (circularProgress, errorString) = createRefs()
        if (state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.constrainAs(circularProgress) {
                    linkTo(start = parent.start, end = parent.end)
                    linkTo(top = parent.top, bottom = parent.bottom)
                },
                color = Color.DarkGray
            )
        }
        if (state.error.isNotBlank()) {
            Text(text = state.error,
                modifier = Modifier.constrainAs(errorString) {
                    linkTo(start = parent.start, end = parent.end)
                    linkTo(top = parent.top, bottom = parent.bottom)
                })
        }
    }
}
