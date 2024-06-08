package com.singnow.app.ui.screens

import android.app.Activity
import android.content.Context
import android.content.pm.ActivityInfo
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.singnow.app.configs.Constant
import com.singnow.app.states.Video
import com.singnow.app.states.viewmodels.VideoViewModel
import com.singnow.app.ui.components.Heading
import com.singnow.app.ui.layouts.NotBottomLayout

class MediaPlayerScreen : NotBottomLayout() {
    private var isDataFetched = false
    private var videoMediaPlayer: Video? = null

    @Composable
    fun Screen(
        videoViewModel: VideoViewModel = viewModel(), videoKey: String
    ) {
        LaunchedEffect(videoKey) {
            videoViewModel.findByVideoKey(videoKey)
        }
        val video by videoViewModel.videoMediaPlayer.asFlow().collectAsState(null)

        Layout(showTopBar = false) {
            if (video != null && !isDataFetched) {
                videoMediaPlayer = video
                videoMediaPlayer?.let { videoPlay ->
                    MediaView(video = videoPlay)
                }
                isDataFetched = true
            }
        }
    }

    @Composable
    private fun MediaView(video: Video) {
        MediaContainer(video = video)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(MaterialTheme.colorScheme.inverseOnSurface),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Heading(value = video.title, size = Constant.TextSize.MD, maxLines = 2)
            }
        }
    }

    @Composable
    private fun MediaContainer(
        context: Context = LocalContext.current,
        activity: Activity = LocalContext.current as Activity,
        video: Video
    ) {
        var isFullScreen by remember {
            mutableStateOf(false)
        }
        val mediaPlayer = ExoPlayer.Builder(context).build().apply {
            setMediaItem(MediaItem.fromUri(video.url))
            prepare()

        }
        val fullScreenHandle: (Boolean) -> Unit = {
            if (it) {
                activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_USER_LANDSCAPE
                isFullScreen = true
            } else {
                activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_USER
                isFullScreen = false
            }
        }
        DisposableEffect(Unit) {
            onDispose {
                mediaPlayer.stop()
                mediaPlayer.release()
            }
        }
        AndroidView(
            factory = {
                PlayerView(context).apply {
                    setFullscreenButtonClickListener {
                        fullScreenHandle(it)
                    }
                    player = mediaPlayer
                    useController = true
                    isAccessibilityFocused

                }
            }, modifier = if (!isFullScreen) Modifier
                .fillMaxWidth()
                .height(200.dp)
            else Modifier.fillMaxSize()
        )
    }
}