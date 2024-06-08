package com.singnow.app.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewmodel.compose.viewModel
import com.singnow.app.states.viewmodels.VideoViewModel
import com.singnow.app.ui.Navigate
import com.singnow.app.ui.Router
import com.singnow.app.ui.components.Heading
import com.singnow.app.ui.layouts.NotBottomLayout

class SearchScreen : NotBottomLayout() {
    private var query = mutableStateOf("")

    @Composable
    fun Screen(videoViewModel: VideoViewModel = viewModel()) {
        videoViewModel.findByName("")
        val videos =
            videoViewModel.videosFindByName.asFlow().collectAsState(initial = emptyList())
        Layout(title = "Search") {
            OutlinedTextField(
                shape = CircleShape,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                value = query.value,
                onValueChange = {
                    query.value = it
                },
                singleLine = true,
                trailingIcon = {
                    IconButton(onClick = {
                        videoViewModel.findByName(query.value)
                    }) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null
                        )
                    }
                }
            )
            if (videos.value.isEmpty()) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(10.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Heading(value = "Result empty")
                }
            } else {
                LazyColumn {
                    items(videos.value) {
                        Card(onClick = {
                            Navigate(Router.MediaPlayerScreen(videoKey = it.key))
                        }) {
                            Text(text = it.title)
                        }
                    }
                }
            }
        }
    }
}