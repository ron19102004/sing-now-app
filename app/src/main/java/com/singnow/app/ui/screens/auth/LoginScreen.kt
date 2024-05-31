package com.singnow.app.ui.screens.auth

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.singnow.app.ui.layouts.AuthLayout

class LoginScreen : AuthLayout() {
    @Composable
    fun Screen() {
        Layout(title = "Login") {
            Text(text = "login")
        }
    }
}