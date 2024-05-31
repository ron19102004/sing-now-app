package com.singnow.app.ui.screens.auth

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.singnow.app.ui.components.Input
import com.singnow.app.ui.components.TextBtn
import com.singnow.app.ui.layouts.AuthLayout

class LoginScreen : AuthLayout() {
    private var username = mutableStateOf("")
    private var password = mutableStateOf("")

    @Composable
    fun Screen() {
        Layout(
            title = "Login",
            modifier = Modifier
                .padding(horizontal = 20.dp)
        ) {
            Input(
                value = username.value,
                onChangeValue = {
                    username.value = it
                },
                label = "Username"
            )
            Spacer(modifier = Modifier.height(5.dp))
            Input(
                value = password.value,
                onChangeValue = {
                    password.value = it
                },
                label = "Password",
                isPassword = true
            )
            Spacer(modifier = Modifier.height(12.dp))
            TextBtn(value = "Sign In", onClick = {})
        }
    }
}