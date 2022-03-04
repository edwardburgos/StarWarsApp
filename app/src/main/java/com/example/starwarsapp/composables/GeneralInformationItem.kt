package com.example.starwarsapp.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.domain.InformationSection

@Composable
fun GeneralInformationItem(section: InformationSection, value: String) {
    Row (
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.padding(vertical = 8.dp).fillMaxWidth()
    ) {
        Text(
            text = section.toString(),
            style = MaterialTheme.typography.h6,
            color = Color.Gray,
            fontWeight = FontWeight.Bold
        )
        Text(text = value.replaceFirstChar { it.uppercase() }, style = MaterialTheme.typography.h6, fontWeight = FontWeight.Bold)
    }
}