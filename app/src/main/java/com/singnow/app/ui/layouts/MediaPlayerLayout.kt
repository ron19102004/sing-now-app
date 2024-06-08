package com.singnow.app.ui.layouts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

open class MediaPlayerLayout {
    @Composable
    fun Layout(
        modifier: Modifier = Modifier,
        verticalArrangement: Arrangement.Vertical = Arrangement.Top,
        horizontalAlignment: Alignment.Horizontal = Alignment.Start,
        bottomBar: @Composable () -> Unit = {},
        content: @Composable () -> Unit,
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = bottomBar
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