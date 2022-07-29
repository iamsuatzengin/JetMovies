package com.suatzengin.whatshouldiwatch.presentation.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.coil.CoilImage
import com.suatzengin.whatshouldiwatch.common.Constants
import com.suatzengin.whatshouldiwatch.data.remote.movie.Movie
import com.suatzengin.whatshouldiwatch.ui.theme.ratingStarColor


@Composable
fun TopRatedHomeItem(
    topRated: Movie,
    onClick: (Movie) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .width(150.dp)
            .clickable {
                onClick(topRated)
            },
    ) {
        CoilImage(
            imageModel = Constants.IMAGE_URL + topRated.posterPath,
            contentScale = ContentScale.Crop,
            shimmerParams = ShimmerParams(
                baseColor = MaterialTheme.colors.background,
                highlightColor = Color.LightGray.copy(alpha = 0.6f),
                durationMillis = 350, dropOff = 0.65f, tilt = 20f
            ),
            circularReveal = CircularReveal(duration = 350),
            failure = { Text(text = "Resim yüklenemedi!") },
            modifier = Modifier
                .width(150.dp)
                .height(220.dp)
                .shadow(elevation = 8.dp, shape = RoundedCornerShape(12.dp)),
        )
        Column(
            modifier = Modifier.padding(top = 12.dp),
        ) {
            Text(text = topRated.originalTitle, fontWeight = FontWeight.Bold)
            Row {
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = "",
                    tint = ratingStarColor,
                    modifier = Modifier.padding(end = 4.dp)
                )
                Text(text = "${topRated.voteAverage}/10", color = Color.LightGray)
            }

        }
    }
}