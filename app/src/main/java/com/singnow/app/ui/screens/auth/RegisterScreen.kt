package com.singnow.app.ui.screens.auth

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.singnow.app.R
import com.singnow.app.states.User
import com.singnow.app.states.objects.AppState
import com.singnow.app.states.viewmodels.AuthViewModel
import com.singnow.app.ui.Navigate
import com.singnow.app.ui.Router
import com.singnow.app.ui.components.Heading
import com.singnow.app.ui.components.Input
import com.singnow.app.ui.components.TextBtn
import com.singnow.app.ui.layouts.AuthLayout

class RegisterScreen : AuthLayout() {
    private lateinit var authViewModel: AuthViewModel
    private var email = mutableStateOf("")
    private var password = mutableStateOf("")
    private var firstName = mutableStateOf("")
    private var lastName = mutableStateOf("")
    private var rePassword = mutableStateOf("")
    private var isErrorRePassword = mutableStateOf(false)
    private var isErrorFirstName = mutableStateOf(false)
    private var isErrorLastName = mutableStateOf(false)
    private var isErrorEmail = mutableStateOf(false)
    private var isErrorPassword = mutableStateOf(false)
    private var errorEmail = mutableStateOf("")
    private var errorRePassword = mutableStateOf("")
    private var errorPassword = mutableStateOf("")
    private var errorFirstName = mutableStateOf("")
    private var errorLastName = mutableStateOf("")

    private var step = mutableIntStateOf(0)
    private fun nextButtonClick() {
        if (email.value.isNotBlank() &&
            firstName.value.isNotBlank() &&
            lastName.value.isNotBlank()
        ) {
            step.intValue = 1
            return
        }
        if (email.value.isEmpty()) {
            isErrorEmail.value = true
            errorEmail.value = "Username is required"
        }
        if (firstName.value.isEmpty()) {
            isErrorFirstName.value = true
            errorFirstName.value = "First name is required"
        }
        if (lastName.value.isEmpty()) {
            isErrorLastName.value = true
            errorLastName.value = "Last name is required"
        }
    }

    private fun onSignUpClick(context: Context) {
        val toast: (String) -> Unit = {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
        if (password.value.isNotBlank() &&
            rePassword.value.isNotBlank() &&
            password.value.length >= 8 &&
            rePassword.value == password.value
        ) {
            authViewModel.register(
                data = User(
                    email.value,
                    password.value,
                    firstName.value,
                    lastName.value
                ),
                error = {},
                success = {
                    toast("Register Success")
                    Navigate(Router.HomeScreen)
                }
            )
            return
        }

        if (password.value.isEmpty()) {
            isErrorPassword.value = true
            errorPassword.value = "Password is required"
        }
        if (password.value.length < 8) {
            isErrorPassword.value = true
            errorPassword.value = "Password must be at least 8 characters"
        }
        if (rePassword.value.isEmpty()) {
            isErrorRePassword.value = true
            errorRePassword.value = "Re password is required"
        }
        if (rePassword.value != password.value) {
            isErrorRePassword.value = true
            errorRePassword.value = "Password not match"
        }
    }

    @Composable
    fun Screen(
        authViewModel: AuthViewModel = AppState.authViewModel,
        context: Context = LocalContext.current
    ) {
        this.authViewModel = authViewModel
        Layout(
            title = "Register",
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
                    when (step.intValue) {
                        0 -> {
                            StepFirstForm()
                        }

                        1 -> {
                            StepEndForm()
                        }
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    when (step.intValue) {
                        0 -> {
                            TextBtn(value = "Next", onClick = { nextButtonClick() })
                        }

                        1 -> {
                            Row {
                                TextBtn(
                                    modifier = Modifier.fillMaxWidth(0.5f),
                                    value = "Back",
                                    onClick = { step.intValue = 0 },
                                    backgroundColor = MaterialTheme.colorScheme.onSecondary,
                                    textColor = MaterialTheme.colorScheme.secondary
                                )
                                Spacer(modifier = Modifier.width(5.dp))
                                TextBtn(
                                    modifier = Modifier.fillMaxWidth(1f),
                                    value = "Sign Up",
                                    onClick = { onSignUpClick(context = context) }
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        }
    }

    @Composable
    private fun StepFirstForm() {
        Input(
            value = email.value,
            onChangeValue = {
                isErrorEmail.value = false
                email.value = it
            },
            label = "Email",
            isError = isErrorEmail.value,
            errorMessage = errorEmail.value
        )
        Spacer(modifier = Modifier.height(5.dp))
        Input(
            value = firstName.value,
            onChangeValue = {
                isErrorFirstName.value = false
                firstName.value = it
            },
            label = "First name",
            isError = isErrorFirstName.value,
            errorMessage = errorFirstName.value
        )
        Spacer(modifier = Modifier.height(5.dp))
        Input(
            value = lastName.value,
            onChangeValue = {
                isErrorLastName.value = false
                lastName.value = it
            },
            label = "Last name",
            isError = isErrorLastName.value,
            errorMessage = errorLastName.value
        )
        Spacer(modifier = Modifier.height(5.dp))
    }

    @Composable
    private fun StepEndForm() {
        Input(
            value = password.value,
            onChangeValue = {
                isErrorPassword.value = false
                password.value = it
            },
            label = "Password",
            isPassword = true,
            isError = isErrorPassword.value,
            errorMessage = errorPassword.value
        )
        Spacer(modifier = Modifier.height(5.dp))
        Input(
            value = rePassword.value,
            onChangeValue = {
                isErrorRePassword.value = false
                rePassword.value = it
            },
            label = "Re password",
            isPassword = true,
            isError = isErrorRePassword.value,
            errorMessage = errorRePassword.value
        )
    }
}