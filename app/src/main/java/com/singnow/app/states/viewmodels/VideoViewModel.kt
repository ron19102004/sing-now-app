package com.singnow.app.states.viewmodels

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
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
import com.singnow.app.states.objects.AppState
import kotlinx.coroutines.delay
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
            "",
            ""
        )
    )
    val videosFindByName = MutableLiveData<List<Video>>()
    val isResearching = mutableStateOf(false)

    init {
        fetchVideos()
    }

    fun findByVideoKey(key: String) {
        viewModelScope.launch {
            videoMediaPlayer.value = Video(
                "",
                "",
                "Unknown",
                "Unknown",
                "",
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
                            it.url,
                            it.lyrics
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
                                it.url,
                                it.lyrics
                            )
                        )
                    }
                }
                videosOnHome.value = videos
            }
        }
    }

    fun findByName(name: String) {
        viewModelScope.launch {
            isResearching.value = true
            delay(1000L)
            videosRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val videos = mutableListOf<Video>()
                    if (snapshot.exists()) {
                        for (child in snapshot.children) {
                            val video = child.getValue(VideoResponse::class.java)
                            if (video != null &&
                                video.title.lowercase().contains(name.lowercase())
                            ) {
                                videos.add(
                                    Video(
                                        child.key!!,
                                        video.image,
                                        video.title,
                                        video.description,
                                        video.url,
                                        video.lyrics
                                    )
                                )
                            }
                        }
                    }
                    videosFindByName.value = videos
                    isResearching.value = false
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")

                }
            })
        }
    }
}