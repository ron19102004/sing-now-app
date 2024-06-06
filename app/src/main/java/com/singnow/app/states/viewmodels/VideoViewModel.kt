package com.singnow.app.states.viewmodels

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.singnow.app.firebase.FirebaseConfig
import com.singnow.app.states.Video
import com.singnow.app.states.VideoResponse
import kotlinx.coroutines.launch

class VideoViewModel : ViewModel() {
    private val videosRef: DatabaseReference = FirebaseConfig.videosRef()
    val videosOnHome = MutableLiveData<List<Video>>(emptyList())
    val videoMediaPlayer = MutableLiveData(
        Video(
            "",
            "",
            "Unknown",
            "Unknown",
            ""
        )
    )

    fun init() {
        fetchVideos()
    }

    fun findByVideoKey(key: String) {
        viewModelScope.launch {
            videoMediaPlayer.value = Video(
                "",
                "",
                "Unknown",
                "Unknown",
                ""
            )
            videosRef.child(key).get().addOnSuccessListener { snapshot ->
                if (snapshot.exists()) {
                    val videoResponse = snapshot.getValue(VideoResponse::class.java)
                    videoResponse?.let {
                        videoMediaPlayer.value = Video(
                            key,
                            it.image,
                            it.title,
                            it.description,
                            it.url
                        )
                        Log.d("Firebase", "Video found with key: $key")
                    }
                } else {
                    Log.d("Firebase", "No video found with key: $key")
                }
            }.addOnFailureListener { exception ->
                Log.e("Firebase", "Database error: ${exception.message}")
            }
        }
    }

    fun fetchVideos() {
        viewModelScope.launch {
            videosRef.get().addOnSuccessListener { snapshot ->
                val videos = mutableListOf<Video>()
                for (userSnapshot in snapshot.children) {
                    val video = userSnapshot.getValue(VideoResponse::class.java)
                    video?.let {
                        videos.add(
                            Video(
                                userSnapshot.key!!,
                                it.image,
                                it.title,
                                it.description,
                                it.url
                            )
                        )
                    }
                }
                videosOnHome.value = videos
            }
        }
    }
}