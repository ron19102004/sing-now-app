package com.singnow.app.ui.layouts

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

open class AuthLayout {
    @Composable
    fun Layout(title: String? = null, content: @Composable () -> Unit) {
        val heightTopBar: Dp = 52.dp
        Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 26.dp)
                    .height(heightTopBar)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(heightTopBar),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Sharp.ArrowBack, contentDescription = null
                        )
                    }
                }
                title?.let {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(heightTopBar),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = it)
                    }
                }
            }
        }) {
            Column(modifier = Modifier.padding(it)) {
                content()
            }
        }
    }
}