package com.singnow.app.ui.screens

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.singnow.app.configs.Constant
import com.singnow.app.states.objects.AppState
import com.singnow.app.states.viewmodels.AuthViewModel
import com.singnow.app.ui.Navigate
import com.singnow.app.ui.Router
import com.singnow.app.ui.components.TextBtn
import com.singnow.app.ui.layouts.MainLayout
import kotlin.random.Random
import kotlin.system.exitProcess


class SettingScreen : MainLayout() {

    @SuppressLint("UnrememberedMutableState")
    @Composable
    fun Screen(
        authViewModel: AuthViewModel = AppState.authViewModel,
        context: Context = LocalContext.current
    ) {
        val initialColors = listOf(
            MaterialTheme.colorScheme.onTertiary,
            MaterialTheme.colorScheme.onTertiary,
            MaterialTheme.colorScheme.onTertiary,
            MaterialTheme.colorScheme.onTertiary,
        )

        val bgColors = remember { mutableStateListOf<Color>().apply { addAll(initialColors) } }

        val bgCs: List<Color> = listOf(
            MaterialTheme.colorScheme.onTertiary,
            MaterialTheme.colorScheme.onSecondary,
            MaterialTheme.colorScheme.outlineVariant
        )

        LaunchedEffect(Unit) {
            for (i in bgColors.indices) {
                val index: Int = getRandomNumber(bgCs.size - 1)
                bgColors[i] = bgCs[index]
            }
        }
        Layout(
            title = "Setting",
            horizontalColumn = 10.dp
        ) {
            if (!authViewModel.isLoggedIn.value) {
                SettingContainer(backgroundColor = bgColors[0]) {
                    Row {
                        TextBtn(
                            value = "Sign In", onClick = {
                                Navigate(Router.LoginScreen)
                            },
                            modifier = Modifier.fillMaxWidth(0.5f),
                            height = 50.dp
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        TextBtn(
                            value = "Sign Up", onClick = {
                                Navigate(Router.RegisterScreen)
                            },
                            modifier = Modifier.fillMaxWidth(1f),
                            height = 50.dp
                        )
                    }
                }
            } else {
                val userCurrent = authViewModel.userCurrent.value
                SettingContainer(backgroundColor = bgColors[2]) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "${userCurrent?.firstName} ${userCurrent?.lastName}",
                            style = TextStyle(
                                fontSize = Constant.TextSize.MD,
                                fontWeight = FontWeight.Bold,
                            )
                        )
                        IconButton(onClick = { Navigate(Router.ProfileScreen) }) {
                            Icon(
                                imageVector = Icons.Default.AccountCircle,
                                contentDescription = null
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
            }
            if (authViewModel.isLoggedIn.value) {
                SettingContainer(backgroundColor = bgColors[1]) {
                    TextBtn(
                        value = "Logout", onClick = {
                            authViewModel.logout()
                        },
                        modifier = Modifier.fillMaxWidth(),
                        height = 50.dp
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            SettingContainer(backgroundColor = bgColors[2]) {
                TextBtn(
                    value = "Exit", onClick = {
                        exitProcess(1)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    height = 50.dp
                )
            }
        }
    }

    @Composable
    private fun SettingContainer(
        backgroundColor: Color = MaterialTheme.colorScheme.onSecondary,
        shape: Shape = MaterialTheme.shapes.small,
        modifier: Modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor, shape),
        paddingContent: Dp = 10.dp,
        content: @Composable () -> Unit
    ) {
        Column(modifier = modifier) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(paddingContent)
            ) {
                content()
            }
        }
    }

    private fun getRandomNumber(n: Int): Int {
        return Random.nextInt(0, n + 1)
    }
}
