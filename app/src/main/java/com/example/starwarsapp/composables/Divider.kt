package com.example.starwarsapp.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun Divider() {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .height(1.dp)
            .background(Color.Gray)
    ) {
    }
}