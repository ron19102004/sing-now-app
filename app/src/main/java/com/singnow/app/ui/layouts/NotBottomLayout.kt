package com.singnow.app.ui.layouts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.singnow.app.ui.components.TopBarContainer

open class NotBottomLayout {
    @Composable
    fun Layout(
        modifier: Modifier = Modifier,
        title: String? = null,
        showTopBar: Boolean = true,
        verticalArrangement: Arrangement.Vertical = Arrangement.Top,
        horizontalAlignment: Alignment.Horizontal = Alignment.Start,
        content: @Composable () -> Unit,
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = { if (showTopBar) TopBarContainer(title = title) }
        ) {
            Column(
                modifier = modifier
                    .padding(it)
                    .fillMaxSize(),
                horizontalAlignment = horizontalAlignment,
                verticalArrangement = verticalArrangement
            ) {
                content()
            }
        }
    }
}