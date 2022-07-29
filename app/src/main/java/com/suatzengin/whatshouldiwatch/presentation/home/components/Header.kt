package com.suatzengin.whatshouldiwatch.presentation.home.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.suatzengin.whatshouldiwatch.ui.theme.categoryColor

@Composable
fun Header(
    header: String,
    onClickSeeMore: (Unit) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = header,
            style = MaterialTheme.typography.h6,
            color = categoryColor,
            modifier = Modifier
                .padding(start = 16.dp, top = 12.dp),
            fontWeight = FontWeight.Bold
        )
        Card(
            modifier = Modifier
                .padding(start = 16.dp, top = 12.dp, end = 8.dp)
                .clickable {
                    onClickSeeMore(Unit)
                },
            border = BorderStroke(
                width = 1.dp, color = Color.LightGray
            ),
            shape = RoundedCornerShape(32.dp)
        ) {
            Text(
                text = "Daha fazla", color = Color.LightGray,
                modifier = Modifier.padding(4.dp),
                fontSize = 12.sp
            )
        }
    }
}