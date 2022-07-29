package com.suatzengin.whatshouldiwatch.presentation.detail.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.coil.CoilImage
import com.suatzengin.whatshouldiwatch.common.Constants
import com.suatzengin.whatshouldiwatch.data.remote.movie_detail.Cast
import com.suatzengin.whatshouldiwatch.ui.theme.categoryColor


@Composable
fun MovieCast(
    cast: List<Cast>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = "Oyuncu Kadrosu", color = categoryColor,
            modifier = Modifier
                .padding(start = 16.dp, top = 12.dp),
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.h6,
        )
        LazyRow(modifier = modifier.padding(8.dp)) {
            items(cast) { castItem ->
                Column(
                    modifier = modifier.height(150.dp).width(120.dp).padding(4.dp),
                ) {
                    CoilImage(
                        imageModel = Constants.IMAGE_URL + castItem.profilePath,
                        circularReveal = CircularReveal(duration = 350),
                        contentScale = ContentScale.Crop,
                        modifier = modifier
                            .size(85.dp)
                            .clip(shape = CircleShape), alignment = Alignment.Center
                    )
                    Text(text = castItem.name, overflow = TextOverflow.Ellipsis)
                    Text(
                        text = castItem.character, color = Color.LightGray,
                        fontSize = 12.sp,
                    )
                }
            }
        }
    }
}