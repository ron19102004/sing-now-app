package com.singnow.app.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TextBtn(
    value: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier.fillMaxWidth(),
    height: Dp = 68.dp,
    backgroundColor: Color = MaterialTheme.colorScheme.onBackground,
    textColor:Color = MaterialTheme.colorScheme.inversePrimary,
    textStyle: TextStyle = TextStyle(
        fontStyle = FontStyle.Normal,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        color = textColor
    )
) {
    TextButton(
        onClick = onClick,
        modifier = modifier.height(height),
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor
        )
    ) {
        Text(text = value, style = textStyle)
    }
}

@Preview(showBackground = true)
@Composable
fun TextBtnPre() {
    TextBtn("text", onClick = {})
}