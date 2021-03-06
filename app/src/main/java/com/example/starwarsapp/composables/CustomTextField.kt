package com.example.starwarsapp.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    paddingLeadingIconEnd: Dp = 0.dp,
    paddingTrailingIconStart: Dp = 0.dp,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
    value: String,
    onValueChange: (query: String) -> Unit
) {
    Row(modifier = modifier,
        verticalAlignment = Alignment.CenterVertically) {
        if (leadingIcon != null) {
            leadingIcon()
        }
        Surface(
            modifier = Modifier.weight(1f)
                .padding(start = paddingLeadingIconEnd, end = paddingTrailingIconStart)
        ) {
            BasicTextField(
                value = value,
                onValueChange = { onValueChange(it) },
                maxLines = 1,
                singleLine = true,
                textStyle = MaterialTheme.typography.body1.plus(TextStyle(color = Color.White)),
                modifier = Modifier.background(Color.Black)
            )
            if (value == "") {
                Text(
                    text = "Search",
                    style = MaterialTheme.typography.body1.plus(TextStyle(color = Color.Gray))
                )
            }
        }
        if (trailingIcon != null && value != "") {
            trailingIcon()
        }
    }
}