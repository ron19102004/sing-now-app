package com.singnow.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.singnow.app.R
import com.singnow.app.states.viewmodels.VideoViewModel
import com.singnow.app.ui.Navigate
import com.singnow.app.ui.Router
import com.singnow.app.ui.components.Heading
import com.singnow.app.ui.components.Loading
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
            if (!videoViewModel.isResearching.value) {
                if (videos.value.isEmpty()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Heading(value = "Result empty")
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                    ) {
                        items(videos.value) {
                            Card(
                                onClick = {
                                    Navigate(Router.MediaPlayerScreen(videoKey = it.key))
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(100.dp),
                            ) {
                                val modifier = Modifier
                                    .fillMaxWidth()
                                    .height(100.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                val image = painterResource(id = R.drawable.videoimage)
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(10.dp),
                                    verticalAlignment = Alignment.Top,
                                    horizontalArrangement = Arrangement.Start
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth(0.4f)
                                            .padding(0.dp, 0.dp, 5.dp, 0.dp)
                                    ) {
                                        AsyncImage(
                                            model = it.image, contentDescription = null,
                                            modifier = modifier,
                                            placeholder = image,
                                            error = image,
                                            contentScale = ContentScale.Crop
                                        )
                                    }
                                    Text(
                                        text = it.title,
                                        maxLines = 3,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(5.dp))
                        }
                    }
                }
            } else {
                Loading(modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp))
            }
        }
    }
}
