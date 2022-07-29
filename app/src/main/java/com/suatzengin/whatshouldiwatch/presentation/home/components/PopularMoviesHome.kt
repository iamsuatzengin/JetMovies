package com.suatzengin.whatshouldiwatch.presentation.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.flowlayout.FlowRow
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.coil.CoilImage
import com.suatzengin.whatshouldiwatch.common.Constants
import com.suatzengin.whatshouldiwatch.data.remote.genre.Genre
import com.suatzengin.whatshouldiwatch.data.remote.movie.Movie
import com.suatzengin.whatshouldiwatch.ui.theme.genreBgColor
import com.suatzengin.whatshouldiwatch.ui.theme.genreColor
import com.suatzengin.whatshouldiwatch.ui.theme.ratingStarColor

@Composable
fun PopularHomeItem(
    popular: Movie,
    genres: List<Genre>,
    onClick: (Movie) -> Unit
) {
    Row(
        modifier = Modifier
            .padding(6.dp)
            .fillMaxWidth()
            .clickable {
                onClick(popular)
            }
    ) {
        CoilImage(
            imageModel = Constants.IMAGE_URL + popular.backdropPath,
            contentScale = ContentScale.Crop,
            shimmerParams = ShimmerParams(
                baseColor = MaterialTheme.colors.background,
                highlightColor = Color.LightGray.copy(alpha = 0.6f),
                durationMillis = 350, dropOff = 0.65f, tilt = 20f
            ),
            circularReveal = CircularReveal(duration = 350),
            failure = { Text(text = "Image request failed!") },
            modifier = Modifier
                .width(150.dp)
                .height(200.dp)
                .clip(shape = RoundedCornerShape(12.dp))
        )
        Column {
            val annotatedString = buildAnnotatedString {
                withStyle(style = SpanStyle(color = Color.Black)) {
                    append(popular.originalTitle)
                }
                withStyle(
                    style = SpanStyle(
                        color = Color.Gray,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Light
                    )
                ) {
                    append(" (${popular.title}) ")
                }
            }
            Text(
                text = annotatedString,
                fontSize = 18.sp,
                modifier = Modifier.padding(start = 8.dp)
            )

            val genreIds = popular.genreIds
            FlowRow(modifier = Modifier.padding(top = 8.dp, start = 8.dp)) {
                genreIds.forEach { id ->
                    genres.forEach { genre ->
                        if (genre.id == id) {
                            Text(
                                text = genre.name, modifier = Modifier
                                    .padding(4.dp)
                                    .background(
                                        color = genreBgColor,
                                        shape = RoundedCornerShape(12.dp)
                                    )
                                    .padding(4.dp), fontSize = 12.sp, color = genreColor
                            )
                        }
                    }
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.Star, contentDescription = "Rating Star",
                    tint = ratingStarColor,
                    modifier = Modifier
                        .padding(start = 8.dp, top = 8.dp)
                        .size(16.dp)
                )
                Text(
                    text = "${popular.voteAverage}/10 IMDb",
                    modifier = Modifier.padding(top = 8.dp, start = 4.dp), color = Color.LightGray,
                    fontSize = 16.sp, textAlign = TextAlign.Center
                )
            }
            Row {
                Icon(
                    imageVector = Icons.Default.CalendarMonth,
                    contentDescription = "Release date icon",
                    modifier = Modifier
                        .padding(start = 8.dp, top = 8.dp)
                        .size(24.dp)
                )
                Text(
                    text = popular.releaseDate, textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 8.dp, start = 4.dp)
                )
            }
        }
    }

}












