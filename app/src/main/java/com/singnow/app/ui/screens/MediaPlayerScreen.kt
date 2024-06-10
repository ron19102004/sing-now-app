package com.singnow.app.ui.screens

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.Settings
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.rememberPermissionState
import com.singnow.app.R
import com.singnow.app.configs.Constant
import com.singnow.app.states.Video
import com.singnow.app.states.viewmodels.RecordMediaViewModel
import com.singnow.app.states.viewmodels.VideoViewModel
import com.singnow.app.ui.components.Heading
import com.singnow.app.ui.layouts.MediaPlayerLayout
import com.singnow.app.ui.layouts.NotBottomLayout
import kotlinx.coroutines.delay

class MediaPlayerScreen : MediaPlayerLayout() {
    private var isDataFetched = false
    private var videoMediaPlayer: Video? = null
    private var mediaPlayer: MutableState<ExoPlayer?> = mutableStateOf(null)

    @RequiresApi(Build.VERSION_CODES.S)
    @Composable
    fun Screen(
        videoViewModel: VideoViewModel = viewModel(),
        recordMediaViewModel: RecordMediaViewModel = viewModel(),
        videoKey: String
    ) {
        LaunchedEffect(videoKey) {
            videoViewModel.findByVideoKey(videoKey)
        }
        val video by videoViewModel.videoMediaPlayer.asFlow().collectAsState(null)

        Layout(bottomBar = {
//            BottomBarMediaPlayer(
//                recordMediaViewModel = recordMediaViewModel
//            )
        }) {
            if (video != null && !isDataFetched) {
                videoMediaPlayer = video
                videoMediaPlayer?.let { videoPlay ->
                    MediaView(video = videoPlay)
                }
                isDataFetched = true
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.S)
    @OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
    @Composable
    private fun BottomBarMediaPlayer(
        context: Context = LocalContext.current,
        recordMediaViewModel: RecordMediaViewModel
    ) {
        var isRecording by remember {
            mutableStateOf(false)
        }
        val recordPermission =
            rememberPermissionState(permission = Manifest.permission.RECORD_AUDIO)
        val storagePermission =
            rememberPermissionState(permission = Manifest.permission.MANAGE_EXTERNAL_STORAGE)
        val manageExternalStorageGranted =
            remember { mutableStateOf(isManageExternalStorageGranted(context)) }
        var isOpenStoragePermission by remember {
            mutableStateOf(false)
        }
        LaunchedEffect(Unit) {
            if (!recordPermission.status.isGranted) {
                recordPermission.launchPermissionRequest()
            }
            if (!storagePermission.status.isGranted && !manageExternalStorageGranted.value) {
                isOpenStoragePermission = true
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 15.dp, start = 10.dp, end = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            BottomBarMediaPlayerItem(icon = {
                Image(
                    painter = painterResource(
                        id = if (isRecording)
                            R.drawable.pauseicon
                        else R.drawable.micicon
                    ),
                    contentDescription = null,
                    modifier = Modifier.size(40.dp),
                    contentScale = ContentScale.Fit
                )
            }, label = "Start", onClick = {
                if (recordPermission.status.isGranted
                    && (manageExternalStorageGranted.value || storagePermission.status.isGranted)
                ) {
                    if (isRecording) {
                        recordMediaViewModel.reset()
                    } else {
                        recordMediaViewModel.start(context)
                    }
                    isRecording = !isRecording
                } else {
                    if (!recordPermission.status.isGranted) {
                        recordPermission.launchPermissionRequest()
                    }
                    if (!storagePermission.status.isGranted
                        && !manageExternalStorageGranted.value
                    ) {
                        isOpenStoragePermission = true
                    }
                }
            })
        }
        if (isOpenStoragePermission) {
            val state = rememberModalBottomSheetState()
            ModalBottomSheet(
                onDismissRequest = { isOpenStoragePermission = false },
                sheetState = state
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(onClick = {
                        val intent = Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION)
                        context.startActivity(intent)
                    }) {
                        Text(text = "Grant Manage External Storage Permission")
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }

    private fun isManageExternalStorageGranted(context: Context): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            Environment.isExternalStorageManager()
        } else {
            true
        }
    }

    @Composable
    private fun BottomBarMediaPlayerItem(
        icon: @Composable () -> Unit,
        label: String,
        onClick: () -> Unit
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(CircleShape)
                .clickable { onClick() },
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            icon()
            Text(text = label)
        }
    }

    @Composable
    private fun MediaView(video: Video) {
        var isOpenDescription by remember {
            mutableStateOf(false)
        }
        MediaContainer(video = video)
        Card(
            onClick = { isOpenDescription = true },
            shape = RoundedCornerShape(10.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.inverseOnSurface
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top
            ) {
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .fillMaxWidth()
                        .padding(10.dp)
                ) {
                    Heading(value = video.title, size = Constant.TextSize.MD, maxLines = 2)
                }
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
        ) {
            Heading(value = "Lyrics", size = Constant.TextSize.MD)
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                item {
                    Text(text = video.lyrics)
                }
            }
        }
        if (isOpenDescription) {
            DescriptionContainer(video = video, onDismissRequest = {
                isOpenDescription = false
            })
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun DescriptionContainer(video: Video, onDismissRequest: () -> Unit) {
        val state = rememberModalBottomSheetState()
        ModalBottomSheet(
            onDismissRequest = { onDismissRequest() },
            sheetState = state
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
            ) {
                item {
                    Heading(value = video.title, size = Constant.TextSize.MD, maxLines = 5)
                    Text(text = video.description)
                }
            }
            Spacer(modifier = Modifier.height(30.dp))
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
        mediaPlayer.value = ExoPlayer.Builder(context).build().apply {
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
                mediaPlayer.value!!.stop()
                mediaPlayer.value!!.release()
            }
        }
        AndroidView(
            factory = {
                PlayerView(context).apply {
                    setFullscreenButtonClickListener {
                        fullScreenHandle(it)
                    }
                    player = mediaPlayer.value
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