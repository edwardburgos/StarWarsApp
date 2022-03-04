package com.example.starwarsapp.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.rememberImagePainter
import coil.size.OriginalSize
import com.example.starwarsapp.R

@ExperimentalComposeUiApi
@Composable
fun LoadingItem(
    imageLoader: ImageLoader
) {
    Row (
        modifier = Modifier.padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = rememberImagePainter(data = R.drawable.loader,
                builder = {
                    size(OriginalSize)
                },
                imageLoader = imageLoader),
            contentDescription = null,
            modifier = Modifier.width(16.dp)
        )
        Text(text = "Loading", color = Color.Gray, style = MaterialTheme.typography.body1, fontWeight = FontWeight.Bold, modifier = Modifier.padding(start = 8.dp))
    }
}