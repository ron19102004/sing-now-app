package com.singnow.app.states.objects

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.singnow.app.states.viewmodels.AuthViewModel

@SuppressLint("StaticFieldLeak")
object InitializationState {
    private var initialized = mutableStateOf(false)
    lateinit var navController: NavHostController
    lateinit var authViewModel: AuthViewModel

    @Composable
    fun start(context: Context) {
        if (!initialized.value) {
            navController = rememberNavController()
            authViewModel = viewModel()
            authViewModel.init(context)
            initialized.value = true
        }
    }
}