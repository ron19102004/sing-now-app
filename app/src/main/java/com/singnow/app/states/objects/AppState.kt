package com.singnow.app.states.objects

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.singnow.app.states.viewmodels.AuthViewModel

object AppState {
    private var initalized = false
    var videoViewModelInit = false

    lateinit var authViewModel: AuthViewModel

    @Composable
    fun Init(context: Context) {
       if(!initalized) {
           authViewModel = viewModel()
           authViewModel.init(context)
           initalized = true
       }
    }
}