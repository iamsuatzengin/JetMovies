package com.suatzengin.whatshouldiwatch.presentation.detail.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.suatzengin.whatshouldiwatch.ui.theme.categoryColor

@Composable
fun MovieDescription(
    description: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = "Description", color = categoryColor,
            modifier = Modifier
                .padding(start = 16.dp, top = 12.dp),
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.h6,
        )
        Text(
            text = description,
            modifier = Modifier.padding(start = 16.dp, top = 8.dp),
            color = Color.Gray, style = MaterialTheme.typography.body2
        )
    }
}
