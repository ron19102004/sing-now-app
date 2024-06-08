package com.singnow.app.ui.screens

import android.content.Context
import android.widget.Space
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.singnow.app.configs.Constant
import com.singnow.app.states.ChangePasswordDto
import com.singnow.app.states.User
import com.singnow.app.states.objects.AppState
import com.singnow.app.states.viewmodels.AuthViewModel
import com.singnow.app.ui.components.Heading
import com.singnow.app.ui.components.Input
import com.singnow.app.ui.components.Loading
import com.singnow.app.ui.components.TextBtn
import com.singnow.app.ui.layouts.NotBottomLayout

class ProfileScreen : NotBottomLayout() {
    private var tabIndex by mutableIntStateOf(0)

    @Composable
    fun Screen(authViewModel: AuthViewModel = AppState.authViewModel) {
        val userCurrent = authViewModel.userCurrent.value
        val tabs = listOf("About", "Password")

        Layout(title = "Profile") {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Heading(value = "${userCurrent?.firstName} ${userCurrent?.lastName}")
            }
            TabRow(selectedTabIndex = tabIndex) {
                tabs.forEachIndexed { index, title ->
                    Tab(text = { Text(title) },
                        selected = tabIndex == index,
                        onClick = { tabIndex = index })
                }
            }
            when (tabIndex) {
                0 -> {
                    if (userCurrent != null) {
                        AboutContainer(
                            userCurrent = userCurrent,
                            authViewModel = authViewModel
                        )
                    } else {
                        Loading()
                    }
                }

                1 -> {
                    if (userCurrent != null) {
                        ChangePasswordContainer(
                            authViewModel = authViewModel
                        )
                    } else {
                        Loading()
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun AboutContainer(
        userCurrent: User,
        authViewModel: AuthViewModel
    ) {
        val isOpenChangeInfo = remember {
            mutableStateOf(false)
        }
        Column(modifier = Modifier.padding(10.dp)) {
            InformationContainer(
                title = "First name",
                value = userCurrent.firstName,
                onClick = {
                }
            )
            Spacer(modifier = Modifier.height(15.dp))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(15.dp))
            InformationContainer(
                title = "Last name",
                value = userCurrent.lastName,
                onClick = {
                }
            )
            Spacer(modifier = Modifier.height(15.dp))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(15.dp))
            InformationContainer(
                title = "Email",
                value = userCurrent.email,
                onClick = {
                }
            )
            Spacer(modifier = Modifier.height(25.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(onClick = {
                    isOpenChangeInfo.value = true
                }) {
                    Text(text = "Change information")
                }
            }
        }
        if (isOpenChangeInfo.value) {
            ChangePasswordModel(
                authViewModel = authViewModel,
                userCurrent = userCurrent,
                onDismissRequest = {
                    isOpenChangeInfo.value = false
                })
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun ChangePasswordModel(
        authViewModel: AuthViewModel,
        userCurrent: User,
        onDismissRequest: () -> Unit,
        context: Context = LocalContext.current
    ) {
        val openChangeInfoState = rememberModalBottomSheetState()
        var firstName by remember {
            mutableStateOf(userCurrent.firstName)
        }
        var lastName by remember {
            mutableStateOf(userCurrent.lastName)
        }
        var email by remember {
            mutableStateOf(userCurrent.email)
        }
        var isFirstNameError by remember {
            mutableStateOf(false)
        }
        var isLastNameError by remember {
            mutableStateOf(false)
        }
        var isEmailError by remember {
            mutableStateOf(false)
        }
        var firstNameError by remember {
            mutableStateOf("")
        }
        var lastNameError by remember {
            mutableStateOf("")
        }
        var emailError by remember {
            mutableStateOf("")
        }
        val submit = {
            if (firstName.isNotBlank() && lastName.isNotBlank() && email.isNotBlank()) {
                authViewModel.changeInfor(
                    User(
                        email,
                        userCurrent.password,
                        firstName,
                        lastName
                    )
                )
                onDismissRequest()
                firstName = ""
                lastName = ""
                email = ""
                Toast.makeText(context, "Information changed", Toast.LENGTH_SHORT)
                    .show()
            } else {
                if (lastName.isEmpty()) {
                    lastNameError = "Last name is empty"
                    isLastNameError = true
                }
                if (firstName.isEmpty()) {
                    firstNameError = "First name is empty"
                    isFirstNameError = true
                }
                if (email.isEmpty()) {
                    emailError = "Email is empty"
                    isEmailError = true
                }
            }
        }
        ModalBottomSheet(
            onDismissRequest = { onDismissRequest() },
            sheetState = openChangeInfoState
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Input(
                    value = firstName, onChangeValue = {
                        isFirstNameError = false
                        firstName = it
                    }, isError = isFirstNameError,
                    errorMessage = firstNameError,
                    label = "First name"
                )
                Spacer(modifier = Modifier.height(10.dp))
                Input(
                    value = lastName, onChangeValue = {
                        isLastNameError = false
                        lastName = it
                    }, isError = isLastNameError,
                    errorMessage = lastNameError,
                    label = "Last name"
                )
                Spacer(modifier = Modifier.height(10.dp))
                Input(
                    value = email, onChangeValue = {
                        isEmailError = false
                        email = it
                    }, isError = isEmailError,
                    errorMessage = emailError,
                    label = "Email"
                )
                Spacer(modifier = Modifier.height(15.dp))
                TextBtn(value = "Submit", onClick = { submit() })

            }
            Spacer(modifier = Modifier.height(40.dp))
        }
    }

    @Composable
    private fun <T> InformationContainer(onClick: (T) -> Unit, title: String, value: T) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(value) }) {
            Text(text = title)
            Text(
                text = value.toString(), style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = Constant.TextSize.MD
                )
            )
        }
    }

    @Composable
    private fun ChangePasswordContainer(
        authViewModel: AuthViewModel,
        context: Context = LocalContext.current
    ) {
        var passwordOld by remember {
            mutableStateOf("")
        }
        var passwordNew by remember {
            mutableStateOf("")
        }
        var passwordConfirm by remember {
            mutableStateOf("")
        }
        var passwordOldError by remember {
            mutableStateOf("")
        }
        var passwordNewError by remember {
            mutableStateOf("")
        }
        var passwordConfirmError by remember {
            mutableStateOf("")
        }
        var isPasswordOldError by remember {
            mutableStateOf(false)
        }
        var isPasswordNewError by remember {
            mutableStateOf(false)
        }
        var isPasswordConfirmError by remember {
            mutableStateOf(false)
        }
        val changeOnClick = {
            if (
                passwordNew.isNotBlank()
                && passwordOld.isNotBlank()
                && passwordConfirm.isNotBlank()
                && passwordNew == passwordConfirm
                && passwordNew.length >= 8
                && passwordOld.length >= 8
            ) {
                authViewModel.changePassword(
                    data = ChangePasswordDto(
                        passwordOld,
                        passwordNew
                    ),
                    success = {
                        tabIndex = 0
                        passwordOld = ""
                        passwordNew = ""
                        passwordConfirm = ""
                        Toast.makeText(context, "Password changed", Toast.LENGTH_SHORT).show()
                    },
                    passwordCurrentError = {
                        passwordOldError = "Password old is incorrect"
                        isPasswordOldError = true
                    }
                )
            } else {
                if (passwordOld.isEmpty()) {
                    passwordOldError = "Password old is empty"
                    isPasswordOldError = true
                }
                if (passwordNew.isEmpty()) {
                    passwordNewError = "Password new is empty"
                    isPasswordNewError = true
                }
                if (passwordConfirm.isEmpty()) {
                    passwordConfirmError = "Password confirm is empty"
                    isPasswordConfirmError = true
                }
                if (passwordNew != passwordConfirm) {
                    passwordConfirmError = "Password new and confirm not match"
                    isPasswordConfirmError = true
                }
                if (passwordNew.length < 8) {
                    passwordNewError = "Password new must be at least 8 characters"
                    isPasswordNewError = true
                }
                if (passwordOld.length < 8) {
                    passwordOldError = "Password old must be at least 8 characters"
                    isPasswordOldError = true
                }
            }

        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Heading(value = "Change password")
        }
        Spacer(modifier = Modifier.height(10.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
        ) {
            Input(
                label = "Password old",
                value = passwordOld,
                onChangeValue = {
                    isPasswordOldError = false
                    passwordOld = it
                },
                isError = isPasswordOldError,
                errorMessage = passwordOldError,
                isPassword = true
            )
            Spacer(modifier = Modifier.height(10.dp))
            Input(
                label = "Password new",
                value = passwordNew,
                onChangeValue = {
                    isPasswordNewError = false
                    passwordNew = it
                },
                isError = isPasswordNewError,
                errorMessage = passwordNewError,
                isPassword = true
            )
            Spacer(modifier = Modifier.height(10.dp))
            Input(
                label = "Password confirm",
                value = passwordConfirm,
                onChangeValue = {
                    isPasswordConfirmError = false
                    passwordConfirm = it
                },
                isError = isPasswordConfirmError,
                errorMessage = passwordConfirmError,
                isPassword = true
            )
            Spacer(modifier = Modifier.height(15.dp))
            TextBtn(value = "Change", onClick = { changeOnClick() })
        }
    }
}