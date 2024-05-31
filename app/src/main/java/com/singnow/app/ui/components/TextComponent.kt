package com.singnow.app.ui.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import com.singnow.app.configs.Constant

@Composable
fun Heading(
    value: String,
    size: TextUnit = Constant.TextSize.MD
) {
    Text(
        text = value,
        style = TextStyle(
            fontSize = size
        )
    )
}

@Preview(showBackground = true)
@Composable
fun HeadingPreview() {
    Heading(value = "Sing Now")
}