package com.example.starwarsapp.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun ErrorItem() {
    Row(
        modifier = Modifier.padding(16.dp)
    ) {
        Text(text = "Failed to load data", color = Color.Red, fontWeight = FontWeight.Bold)
    }
}