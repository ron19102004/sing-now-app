package com.singnow.app.ui.layouts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.singnow.app.ui.NavigationBarBottomContainer

open class MainLayout {
    @Composable
    fun Layout(
        modifier: Modifier = Modifier,
        verticalArrangement: Arrangement.Vertical = Arrangement.Top,
        horizontalAlignment: Alignment.Horizontal = Alignment.Start,
        content: @Composable () -> Unit,
    ) {
        Scaffold(
            modifier = Modifier,
            bottomBar = { NavigationBarBottomContainer() }
        ) {
            Column(
                modifier = modifier.padding(it),
                horizontalAlignment = horizontalAlignment,
                verticalArrangement = verticalArrangement
            ) {
                content()
            }
        }
    }
}