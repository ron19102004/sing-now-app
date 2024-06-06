package com.singnow.app.ui.screens.auth

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.singnow.app.R
import com.singnow.app.states.LoginDto
import com.singnow.app.states.objects.InitializationState
import com.singnow.app.states.viewmodels.AuthViewModel
import com.singnow.app.ui.Navigate
import com.singnow.app.ui.Router
import com.singnow.app.ui.components.Heading
import com.singnow.app.ui.components.Input
import com.singnow.app.ui.components.TextBtn
import com.singnow.app.ui.layouts.AuthLayout

class LoginScreen : AuthLayout() {
    private lateinit var authViewModel: AuthViewModel
    private var email = mutableStateOf("")
    private var password = mutableStateOf("")
    private var isErrorUsername = mutableStateOf(false)
    private var isErrorPassword = mutableStateOf(false)
    private var errorUsername = mutableStateOf("")
    private var errorPassword = mutableStateOf("")

    private var enableSignInButton = mutableStateOf(true)
    private var displaySignUpButton = mutableStateOf(false)

    private fun onSignInClick(context: Context) {
        val toast: (String) -> Unit = {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
        if (email.value.isNotBlank() && password.value.isNotBlank() && password.value.length >= 8) {
            authViewModel.login(
                data = LoginDto(
                    email.value,
                    password.value
                ),
                startLoading = {
                    enableSignInButton.value = false
                },
                loginSuccess = {
                    enableSignInButton.value = true
                    displaySignUpButton.value = false
                    Navigate(Router.HomeScreen)
                },
                loginFailed = {
                    toast("Password is incorrect")
                    enableSignInButton.value = true
                },
                error = {
                    enableSignInButton.value = true
                },
                emailNotFound = {
                    enableSignInButton.value = true
                    displaySignUpButton.value = true
                    toast("Email not found")
                },
            )
            return
        }
        if (email.value.isEmpty()) {
            isErrorUsername.value = true
            errorUsername.value = "Username is required"
        }
        if (password.value.isEmpty()) {
            isErrorPassword.value = true
            errorPassword.value = "Password is required"
        } else if (password.value.length < 8) {
            isErrorPassword.value = true
            errorPassword.value = "Password must be at least 8 characters"
        }
    }

    @Composable
    fun Screen(
        authViewModel: AuthViewModel = InitializationState.authViewModel,
        context: Context = LocalContext.current
    ) {
        this.authViewModel = authViewModel
        Layout(
            title = "Login",
            modifier = Modifier
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.Bottom
        ) {
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                item {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.microphone),
                            contentDescription = null,
                            modifier = Modifier
                                .size(150.dp)
                                .clip(CircleShape)
                                .clickable { Navigate(Router.HomeScreen) },
                        )
                        Heading(value = "Sing Now")
                    }
                    Spacer(modifier = Modifier.height(30.dp))
                    Input(
                        value = email.value,
                        onChangeValue = {
                            email.value = it
                        },
                        label = "Username",
                        isError = isErrorUsername.value,
                        errorMessage = errorUsername.value
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Input(
                        value = password.value,
                        onChangeValue = {
                            password.value = it
                        },
                        label = "Password",
                        isPassword = true,
                        isError = isErrorPassword.value,
                        errorMessage = errorPassword.value
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    TextBtn(value = "Sign In", onClick = { onSignInClick(context = context) })
                    if (displaySignUpButton.value) {
                        Spacer(modifier = Modifier.height(12.dp))
                        TextBtn(
                            value = "Sign Up",
                            onClick = {
                                Navigate(Router.RegisterScreen)
                            },
                            backgroundColor = MaterialTheme.colorScheme.onSecondary,
                            textColor = MaterialTheme.colorScheme.secondary
                        )
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        }
    }
}
