package com.example.wordlemix.reusableItems

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp

@Composable
fun Headline(headlineText: String) {
    Text(
        text = headlineText, color = Color(0xFFE5EBEF),
        style = MaterialTheme.typography.bodyLarge,
    )
}