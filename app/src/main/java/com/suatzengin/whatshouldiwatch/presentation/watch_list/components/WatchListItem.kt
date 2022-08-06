package com.suatzengin.whatshouldiwatch.presentation.watch_list.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.coil.CoilImage
import com.suatzengin.whatshouldiwatch.common.Constants
import com.suatzengin.whatshouldiwatch.data.local.entities.MovieEntity
import com.suatzengin.whatshouldiwatch.ui.theme.ratingStarColor
import kotlin.time.Duration.Companion.minutes


@Composable
fun WatchListItem(
    movie: MovieEntity,
    onDelete: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val openDialog = remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .height(if (expanded) Int.MAX_VALUE.dp else 200.dp)
            .clickable { expanded = !expanded },
        elevation = 4.dp,
        shape = RoundedCornerShape(12.dp),
    ) {
        Column {
            Row(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
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
                    IconButton(
                        onClick = { openDialog.value = true },
                        modifier = Modifier.size(18.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Delete, contentDescription = "")
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
            if (openDialog.value) {
                DeleteAlertDialog(
                    title = movie.title,
                    isOpendDialog = openDialog,
                    onDelete = onDelete
                )
            }
        }
    }
}
