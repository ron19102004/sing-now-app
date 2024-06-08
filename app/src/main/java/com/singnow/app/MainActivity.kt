package com.singnow.app

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.FirebaseApp
import com.singnow.app.firebase.FirebaseConfig
import com.singnow.app.states.VideoResponse
import com.singnow.app.states.objects.AppState
import com.singnow.app.states.viewmodels.AuthViewModel
import com.singnow.app.ui.RouterSetup
import com.singnow.app.ui.theme.SingNowTheme

@SuppressLint("StaticFieldLeak")
lateinit var navController: NavHostController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        FirebaseApp.initializeApp(this)
        setContent {
            AppState.Init(this)
            navController = rememberNavController()
            SingNowTheme {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    RouterSetup(navController)
                }
            }
        }
    }
}