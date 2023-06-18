package com.example.mp3downloader.presentation.home_screen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arthenica.mobileffmpeg.Config
import com.arthenica.mobileffmpeg.FFmpeg
import com.example.mp3downloader.data.model.DownloadProgressInfo
import com.example.mp3downloader.data.model.VideoInfo
import com.example.mp3downloader.mainMod
import com.squareup.moshi.Moshi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val moshi: Moshi
) : ViewModel() {
    var state by mutableStateOf(HomeScreenState())
        private set

    val videoInfoJsonAdapter = moshi.adapter(VideoInfo::class.java)
    val downloadInfoJsonAdapter = moshi.adapter(DownloadProgressInfo::class.java)

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
                )
                Log.d("Girish", "grabInfo: $output")
                val videoInfo: VideoInfo = videoInfoJsonAdapter.fromJson(output.toString())
                    ?: throw Exception("Error while parsing video info json")
                state = state.copy(
                    step = HomeScreenStep.DOWNLOADING,
                    videoInfo = videoInfo
                )
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
        try {
            val downloadProgressInfo: DownloadProgressInfo =
                downloadInfoJsonAdapter.fromJson(message)
                    ?: throw Exception("Error while parsing download progress info json")
            Log.d("Girish", "callbackFromMain: $downloadProgressInfo")
            state = state.copy(
                downloadPercentComplete = downloadProgressInfo.percentComplete,
            )
        } catch (e: Exception) {
            Log.d("Girish", "callbackFromMain: ${e.message}")
        }
    }

    fun downloadAudio() {
        viewModelScope.launch {
            Log.d("Girish", "downloadAudio: started")
            try {
                val output = mainMod().callAttr(
                    "download_audio",
                    state.youtubeLink,
                    this@HomeViewModel::callbackFromMain
                )
                Log.d("Girish", "downloadAudio: $output")
                state = state.copy(
                    step = HomeScreenStep.CONVERTING,
                )
            } catch (e: Exception) {
                state = state.copy(
                    step = HomeScreenStep.FAILED,
                    errorMessage = e.message ?: "Unknown error"
                )
                Log.d("Girish", "downloadAudio: error=${e.stackTrace}")
            }
            Log.d("Girish", "downloadAudio: finished")
        }
    }

    fun convertAudio(rootPath: String) {

        val inputFile = "${rootPath + File.separator}\"${state.videoInfo!!.title}.m4a\""
        val outputFile = "${rootPath + File.separator}\"${state.videoInfo!!.simplifiedTitle}.mp3\""

        Log.d("Girish", "convertAudio: $inputFile")
        Log.d("Girish", "convertAudio: $outputFile")

        Config.resetStatistics()
        FFmpeg.executeAsync(
            "-i $inputFile -c:v copy -c:a libmp3lame -q:a 4 $outputFile"
        ) { executionId, returnCode ->
            if (returnCode == Config.RETURN_CODE_SUCCESS) {
                Log.i("Girish", "Async command execution completed successfully.")
                state = state.copy(
                    step = HomeScreenStep.SAVING
                )
            } else if (returnCode == Config.RETURN_CODE_CANCEL) {
                Log.i("Girish", "Async command execution cancelled by user.")
            } else {
                Log.i(
                    "Girish",
                    "Async command execution failed with returnCode=$returnCode"
                )
            }
        }
    }

    fun saveAudio() {

    }

    fun toastErrorMessageShown() {
        state = state.copy(toastErrorMessage = null)
    }

}