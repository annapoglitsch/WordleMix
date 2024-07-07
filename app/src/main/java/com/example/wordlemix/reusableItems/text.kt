package com.example.wordlemix.reusableItems

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun Headline(headlineText: String) {
    Text(
        text = headlineText, color = MaterialTheme.colorScheme.tertiary,
        style = MaterialTheme.typography.bodyLarge,
    )
}