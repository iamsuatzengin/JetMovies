package com.suatzengin.whatshouldiwatch.presentation.watch_list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.coil.CoilImage
import com.suatzengin.whatshouldiwatch.R
import com.suatzengin.whatshouldiwatch.common.Constants
import com.suatzengin.whatshouldiwatch.data.local.entities.MovieEntity
import com.suatzengin.whatshouldiwatch.ui.theme.ratingStarColor
import kotlin.time.Duration.Companion.minutes


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun WatchListScreen(
    viewModel: WatchListViewModel = hiltViewModel()
) {
    val state = viewModel.state.value

    Scaffold(
        modifier = Modifier
            .statusBarsPadding()
            .imePadding(),
        topBar = {
            TopAppBar(
                title = { Text(text = "İzleme Listem") },
                backgroundColor = Color.Transparent,
                elevation = 0.dp
            )
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            LazyColumn(modifier = Modifier.padding(it)) {
                items(items = state.list) { item ->

                    val dismissState = rememberDismissState(
                        confirmStateChange = { dismissValue ->
                            if (dismissValue == DismissValue.DismissedToStart) {
                                viewModel.onEvent(WatchListEvent.SwipeToDelete(item))

                            }
                            true
                        }
                    )

                    SwipeToDismiss(
                        state = dismissState,
                        background = {
                            val direction = dismissState.dismissDirection ?: return@SwipeToDismiss
                            val color by animateColorAsState(
                                when (direction) {
                                    DismissDirection.EndToStart -> Color.Red
                                    DismissDirection.StartToEnd -> Color.Transparent
                                }
                            )
                            val scale by animateFloatAsState(
                                if (dismissState.targetValue == DismissValue.Default) 0.75f else 1f
                            )
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(color = color)
                                    .padding(horizontal = 20.dp),
                                contentAlignment = Alignment.CenterEnd
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete, contentDescription = "",
                                    modifier = Modifier.scale(scale = scale)
                                )
                            }
                        },
                        dismissThresholds = { FractionalThreshold(0.2f) },
                        directions = setOf(DismissDirection.EndToStart),
                        dismissContent = {
                            WatchListItem(
                                dismissDirection = dismissState.dismissDirection,
                                movie = item
                            )
                        }
                    )

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

@Composable
fun WatchListItem(
    dismissDirection: DismissDirection?,
    movie: MovieEntity
) {
    var expanded by remember { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .height(if (expanded) Int.MAX_VALUE.dp else 200.dp),
        elevation = animateDpAsState(
            targetValue = if (dismissDirection != null) 8.dp else 2.dp
        ).value,
        shape = RoundedCornerShape(12.dp),
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                CoilImage(
                    imageModel = Constants.IMAGE_URL + movie.posterPath,
                    contentScale = ContentScale.Crop,
                    shimmerParams = ShimmerParams(
                        baseColor = MaterialTheme.colors.background,
                        highlightColor = Color.LightGray.copy(alpha = 0.6f),
                        durationMillis = 350, dropOff = 0.65f, tilt = 20f
                    ),
                    circularReveal = CircularReveal(duration = 350),
                    failure = { Text(text = "Image request failed!") },
                    modifier = Modifier
                        .height(200.dp)
                        .width(120.dp)
                        .shadow(elevation = 8.dp, shape = RoundedCornerShape(12.dp)),
                )
                Column(
                    modifier = Modifier
                        .padding(start = 12.dp)
                        .height(200.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    val annotatedString = buildAnnotatedString {
                        withStyle(style = SpanStyle(color = Color.Black)) {
                            append(movie.originalTitle)
                        }
                        withStyle(
                            style = SpanStyle(
                                color = Color.Gray,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Light
                            )
                        ) {
                            append(" (${movie.title}) ")
                        }
                    }
                    Text(text = annotatedString, style = MaterialTheme.typography.h6)
                    Row {
                        Icon(
                            imageVector = Icons.Default.Star, contentDescription = "",
                            modifier = Modifier.size(24.dp),
                            tint = ratingStarColor
                        )
                        Text(text = "${movie.voteAverage}/10", color = Color.LightGray)
                    }
                    Row {
                        Text(text = "Time: ", color = Color.LightGray)
                        Text(text = "${movie.runtime?.minutes}")

                    }

                    IconButton(onClick = { expanded = !expanded }) {
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown, contentDescription = ""
                        )
                    }
                }
            }
            AnimatedVisibility(visible = expanded) {
                Text(
                    text = "${movie.overview}",
                    style = MaterialTheme.typography.subtitle2,
                    maxLines = if (expanded) Int.MAX_VALUE else 1,
                    modifier = Modifier.padding(12.dp)
                )
            }
        }


    }
}
