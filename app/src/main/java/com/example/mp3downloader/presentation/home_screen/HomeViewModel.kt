package com.example.mp3downloader.presentation.home_screen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mp3downloader.data.model.VideoInfo
import com.example.mp3downloader.mainMod
import com.squareup.moshi.JsonAdapter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val jsonAdapter: JsonAdapter<VideoInfo>
) : ViewModel() {
    var state by mutableStateOf(HomeScreenState())
        private set

    fun onEvent(event: HomeScreenEvents) {
        when (event) {
            is HomeScreenEvents.OnYoutubeLinkChange -> {
                state = state.copy(youtubeLink = event.youtubeLink)
            }

            is HomeScreenEvents.OnDestinationFolderChange -> {
                state = state.copy(destinationFolder = event.destinationFolder)
            }

            is HomeScreenEvents.OnVideoGrabInfo -> {
                state = state.copy(
                    step = HomeScreenStep.GRABBING
                )
                grabInfo()
            }
        }
    }

    private fun grabInfo() {
        viewModelScope.launch {
            delay(500)
            try {
                val output = mainMod().callAttr(
                    "get_video_info",
                    state.youtubeLink,
                    this@HomeViewModel::callbackFromMain
                ).toString().replace('\'', '"')

                val videoInfo: VideoInfo = jsonAdapter.fromJson(output)
                    ?: throw Exception("Error while parsing json")
                state = state.copy(
                    step = HomeScreenStep.DOWNLOADING,
                    videoInfo = videoInfo
                )
//                downloadAudio()
            } catch (e: Exception) {
                state = state.copy(
                    step = HomeScreenStep.INITIAL,
                    toastErrorMessage = "Failed to grab video info",
                    videoInfo = null
                )
                Log.d("Girish", "grabInfo: error=${e.message}")
            }
        }
    }

    fun callbackFromMain(message: String) {
        Log.d("Girish", "callbackFromMain: $message")
    }

    private fun downloadAudio() {
        viewModelScope.launch {
            try {
                val output = mainMod().callAttr(
                    "download_audio",
                    state.youtubeLink
                )
                Log.d("Girish", "downloadAudio: $output")
                state = state.copy(
                    step = HomeScreenStep.CONVERTING,
                )
            } catch (e: Exception) {
                state = state.copy(
                    step = HomeScreenStep.FAILED,
                    errorMessage = e.stackTraceToString()
                )
                Log.d("Girish", "downloadAudio: error=${e.message}")
            }
        }
    }

    fun toastErrorMessageShown() {
        state = state.copy(toastErrorMessage = null)
    }

}