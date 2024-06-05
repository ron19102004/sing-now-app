package com.singnow.app.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Input(
    value: String,
    onChangeValue: (String) -> Unit,
    modifier: Modifier = Modifier.fillMaxWidth(),
    label: String? = null,
    placeholder: @Composable (() -> Unit)? = null,
    singleLine: Boolean = true,
    shape: RoundedCornerShape = CircleShape,
    height: Dp = 74.dp,
    textStyle: TextStyle = TextStyle(
        fontSize = 18.sp
    ),
    isError: Boolean = false,
    errorMessage: String? = null,
    isPassword: Boolean = false
) {
    OutlinedTextField(
        value = value,
        onValueChange = onChangeValue,
        modifier = modifier.height(height),
        placeholder = placeholder,
        label = {
            if (!errorMessage.isNullOrBlank()) {
                Text(text = errorMessage)
            } else {
                label?.let {
                    Text(text = it)
                }
            }
        },
        singleLine = singleLine,
        shape = shape,
        textStyle = textStyle,
        isError = isError,
        visualTransformation = if (isPassword)
            PasswordVisualTransformation()
        else VisualTransformation.None
    )
}

@Composable
@Preview(showBackground = true)
fun InputPre() {
    Input(value = "value", onChangeValue = {})
}