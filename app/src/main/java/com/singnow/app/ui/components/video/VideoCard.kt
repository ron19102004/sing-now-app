package com.singnow.app.ui.components.video

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.singnow.app.R
import com.singnow.app.states.Video
import com.singnow.app.ui.Navigate
import com.singnow.app.ui.Router

@Composable
fun VideoCard(video: Video) {
    Card {
        val modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
        val image = painterResource(id = R.drawable.videoimage)
        Box(modifier = modifier) {
            AsyncImage(
                model = video.image, contentDescription = null,
                modifier = modifier,
                placeholder = image,
                error = image,
                contentScale = ContentScale.Crop
            )
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.Bottom
            ) {
                TextButton(onClick = {
                    Navigate(Router.MediaPlayerScreen(videoKey = video.key))
                }, modifier = Modifier.padding(10.dp)) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(text = "Play")
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Text(text = video.title)
        }
    }
}