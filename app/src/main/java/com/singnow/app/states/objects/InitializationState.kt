package com.singnow.app.states.objects

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@SuppressLint("StaticFieldLeak")
object InitializationState {
    private var initialized = mutableStateOf(false)
    lateinit var navController: NavHostController

    @Composable
    fun start() {
        if (!initialized.value) {
            navController = rememberNavController()
            initialized.value = true
        }
    }
}