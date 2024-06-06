package com.singnow.app.ui.layouts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.singnow.app.ui.NavigationBarBottomContainer
import com.singnow.app.ui.components.TopBarContainer

open class MainLayout {
    @Composable
    fun Layout(
        modifier: Modifier = Modifier,
        verticalArrangement: Arrangement.Vertical = Arrangement.Top,
        horizontalAlignment: Alignment.Horizontal = Alignment.Start,
        title: String? = null,
        content: @Composable () -> Unit,
    ) {
        Scaffold(
            modifier = Modifier,
            topBar = { title?.let { TopBarContainer(isDisplayBackIcon = false, title = it) } },
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