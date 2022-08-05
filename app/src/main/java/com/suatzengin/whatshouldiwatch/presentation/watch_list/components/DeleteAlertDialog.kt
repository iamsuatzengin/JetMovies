package com.suatzengin.whatshouldiwatch.presentation.watch_list.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun DeleteAlertDialog(
    title: String,
    isOpendDialog: MutableState<Boolean>,
    onDelete: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { isOpendDialog.value = false },
        title = { Text(text = "Delete ${title}?") },
        text = { Text(text = "Do you want to delete this?") },
        confirmButton = {
            TextButton(
                onClick = {
                    isOpendDialog.value = false
                    onDelete()
                }) {
                Text(text = "Yes", color = Color.Red)
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    isOpendDialog.value = false
                }) {
                Text(text ="Cancel",color = Color.Red)
            }
        },
        shape = RoundedCornerShape(16.dp),

    )
}