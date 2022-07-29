package com.suatzengin.whatshouldiwatch.presentation.detail.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.coil.CoilImage
import com.suatzengin.whatshouldiwatch.common.Constants
import com.suatzengin.whatshouldiwatch.data.remote.movie.Movie
import com.suatzengin.whatshouldiwatch.ui.theme.categoryColor

@Composable
fun SimilarMovies(
    similar: List<Movie>,
    modifier: Modifier = Modifier,
    onClickItem: (Movie) -> Unit
) {
    Column(modifier = modifier) {
        Text(
            text = "Benzer", color = categoryColor,
            modifier = Modifier
                .padding(start = 16.dp, top = 8.dp),
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.h6,
        )
        LazyRow(modifier = modifier.padding(8.dp)) {
            items(similar) { similarMovie ->
                Card(
                    elevation = 4.dp,
                    shape = RoundedCornerShape(12.dp),
                    modifier = modifier
                        .width(150.dp)
                        .height(200.dp)
                        .padding(8.dp)
                        .clickable { onClickItem(similarMovie) }
                ) {
                    CoilImage(
                        imageModel = Constants.IMAGE_URL + similarMovie.posterPath,
                        circularReveal = CircularReveal(duration = 350),
                        contentScale = ContentScale.FillHeight
                    )
                }
            }
        }
    }
}