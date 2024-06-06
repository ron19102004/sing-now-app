package com.singnow.app.ui.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import com.singnow.app.configs.Constant

@Composable
fun Heading(
    value: String,
    size: TextUnit = Constant.TextSize.LG,
    fontWeight: FontWeight = FontWeight.Bold,
    fontStyle: FontStyle = FontStyle.Normal,
    textStyle: TextStyle = TextStyle(
        fontSize = size,
        fontWeight = fontWeight,
        fontStyle = fontStyle
    ),
    maxLines: Int = 1
) {
    Text(
        text = value,
        style = textStyle,
        maxLines = maxLines,
        overflow = TextOverflow.Ellipsis
    )
}

@Preview(showBackground = true)
@Composable
fun HeadingPreview() {
    Heading(value = "Sing Now")
}
