package com.singnow.app.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewmodel.compose.viewModel
import com.singnow.app.states.objects.AppState
import com.singnow.app.states.viewmodels.AuthViewModel
import com.singnow.app.states.viewmodels.VideoViewModel
import com.singnow.app.ui.Navigate
import com.singnow.app.ui.Router
import com.singnow.app.ui.components.PullToRefreshLazyColumn
import com.singnow.app.ui.components.video.VideoCard
import com.singnow.app.ui.layouts.MainLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeScreen : MainLayout() {
    private var isRefreshing = mutableStateOf(false)

    @Composable
    fun Screen(
        authViewModel: AuthViewModel =  AppState.authViewModel,
        videoViewModel: VideoViewModel =  viewModel(),
        scope: CoroutineScope = rememberCoroutineScope()
    ) {
        val user = authViewModel.userCurrent.value
        val title: String = if (user != null) {
            "Welcome ${user.lastName}"
        } else "Sign Now"
        val videos = videoViewModel.videosOnHome.asFlow().collectAsState(initial = emptyList())
        Layout(title = title) {
            OutlinedTextField(
                enabled = false,
                value = "",
                onValueChange = {},
                placeholder = { Text("Search...") },
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .fillMaxWidth()
                    .clip(CircleShape)
                    .height(50.dp)
                    .clickable {
                        Navigate(Router.SearchScreen)
                    },
                shape = CircleShape,
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null
                    )
                }
            )
            Spacer(modifier = Modifier.height(10.dp))
            PullToRefreshLazyColumn(
                isRefreshing = isRefreshing.value,
                onRefresh = {
                    scope.launch {
                        isRefreshing.value = true
                        videoViewModel.fetchVideos()
                        delay(1000L)
                        isRefreshing.value = false
                    }
                },
                items = videos.value,
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .fillMaxWidth(),
                content = {
                    VideoCard(video = it)
                    Spacer(modifier = Modifier.height(20.dp))
                }
            )
        }
    }
}