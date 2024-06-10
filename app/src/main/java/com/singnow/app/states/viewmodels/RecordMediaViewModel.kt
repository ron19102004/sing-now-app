package com.singnow.app.states.viewmodels

import android.content.Context
import android.media.MediaRecorder
import android.os.Build
import android.os.Environment
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import java.io.File
import java.io.IOException


class RecordMediaViewModel : ViewModel() {
    private var recordings = mutableStateOf(listOf<File>())
    private var recorder = mutableStateOf<MediaRecorder?>(null)
    private var currentOutputFile = mutableStateOf<File?>(null)
    fun reset() {
        recorder.value?.let {
            stop(it)
        }
        recordings.value += currentOutputFile.value!!
        recorder.value = null
        currentOutputFile.value = null
    }

    @RequiresApi(Build.VERSION_CODES.S)
    fun start(context: Context) {
            currentOutputFile.value = File(
                context.getExternalFilesDir(Environment.DIRECTORY_RECORDINGS),
                "sing_now_recording_${System.currentTimeMillis()}.mp3"
            )
        recorder.value = MediaRecorder().apply {
            setUp(this, currentOutputFile.value!!)
        }
    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun setUp(recorder: MediaRecorder, outputFile: File) {
        recorder.apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            setOutputFile(outputFile.absoluteFile)
            try {
                prepare()
                start()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun stop(recorder: MediaRecorder) {
        recorder.apply {
            try {
                stop()
            } catch (e: Exception) {
                e.printStackTrace()
                release()
            } finally {
                reset()
            }
        }
    }
}