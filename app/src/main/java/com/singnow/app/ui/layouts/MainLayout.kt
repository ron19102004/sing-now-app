package com.singnow.app.ui.layouts

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.singnow.app.ui.NavigationBarBottomContainer

open class MainLayout {
    @Composable
    fun Layout(content: @Composable () -> Unit) {
        Scaffold(
            bottomBar = { NavigationBarBottomContainer() }
        ) {
            Column(modifier = Modifier.padding(it)) {
                content()
            }
        }
    }
}