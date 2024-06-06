package com.singnow.app.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.singnow.app.states.User
import com.singnow.app.states.objects.InitializationState
import com.singnow.app.states.viewmodels.AuthViewModel
import com.singnow.app.ui.components.Heading
import com.singnow.app.ui.components.Input
import com.singnow.app.ui.components.TextBtn
import com.singnow.app.ui.layouts.NotBottomLayout

class ProfileScreen : NotBottomLayout() {
    @Composable
    fun Screen(authViewModel: AuthViewModel = InitializationState.authViewModel) {
        val userCurrent = authViewModel.userCurrent.value
        var tabIndex by remember { mutableIntStateOf(0) }
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
                    Text(text = "First name: ${userCurrent?.firstName}")
                    Text(text = "Last name: ${userCurrent?.lastName}")
                }

                1 -> {
                    if (userCurrent != null) {
                        ChangePasswordContainer(
                            authViewModel = authViewModel, userCurrent = userCurrent
                        )
                    }
                }
            }
        }
    }

    @Composable
    private fun ChangePasswordContainer(
        authViewModel: AuthViewModel, userCurrent: User
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
                errorMessage = passwordOldError
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
                errorMessage = passwordNewError
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
                errorMessage = passwordConfirmError
            )
            Spacer(modifier = Modifier.height(15.dp))
            TextBtn(value = "Change", onClick = { changeOnClick() })
        }
    }
}