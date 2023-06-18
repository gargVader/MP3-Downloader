package com.example.mp3downloader.presentation.home_screen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chaquo.python.Python
import com.example.mp3downloader.data.model.VideoInfo
import com.squareup.moshi.JsonAdapter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val jsonAdapter: JsonAdapter<VideoInfo>
) : ViewModel() {

    val pyObj = Python.getInstance().getModule("main")
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

                viewModelScope.launch {
                    try {
                        val output = pyObj.callAttr(
                            "get_video_info",
                            state.youtubeLink
                        ).toString().replace('\'', '"')
                        val videoInfo: VideoInfo = jsonAdapter.fromJson(output)
                            ?: throw Exception("Error while parsing json")
                        state = state.copy(
                            step = HomeScreenStep.DOWNLOADING,
                            videoInfo = videoInfo
                        )
                    } catch (e: Exception) {
                        state = state.copy(
                            step = HomeScreenStep.INITIAL,
                            isGrabbingError = true,
                            videoInfo = null
                        )
                        Log.d("Girish", "onEvent: ${e.message}")
                    }
                }

//                viewModelScope.launch {
//                    val pyObj = Python.getInstance()
//                        .getModule("main")
//                    val output1 = pyObj.callAttr(
//                        "get_video_info",
//                        "https://www.youtube.com/watch?v=5Eqb_-j3FDA"
//                    )
//                    val output2 = pyObj.callAttr(
//                        "download_audio",
//                        "https://www.youtube.com/watch?v=5Eqb_-j3FDA"
//                    )
//                    val jsonObj = JSONObject(output1.toString())
//                    Log.d("Girish", "HomeScreen: ${output1}")
//                    val inputFileName =
//                        "Coke Studio ｜ Season 14 ｜ Pasoori ｜ Ali Sethi x Shae Gill.m4a"
//                    Config.resetStatistics()
//                    FFmpeg.executeAsync(
//                        "-i ${event.rootPath + File.separator}\"$inputFileName\" -c:v copy -c:a libmp3lame -q:a 4 ${event.rootPath + File.separator}output.mp3"
//                    ) { executionId, returnCode ->
//                        if (returnCode == RETURN_CODE_SUCCESS) {
//                            Log.i("Girish", "Async command execution completed successfully.")
//
//                        } else if (returnCode == RETURN_CODE_CANCEL) {
//                            Log.i("Girish", "Async command execution cancelled by user.")
//                        } else {
//                            Log.i(
//                                "Girish",
//                                "Async command execution failed with returnCode=$returnCode"
//                            )
//                        }
//                    }
//                    val internalStorageFilePath = "${event.rootPath + File.separator}output.mp3"
//                    val destinationUri = state.destinationFolder
//
//
//                    Log.d("Girish", "onEvent: ${state.destinationFolder}")
//                    Log.d("Girish", "onEvent: ${state.destinationFolder.path}")
//                    Log.d("Girish", "onEvent: $internalStorageFilePath")
//                }
            }
        }
    }

    fun grabbingErrorMessageShown() {
        state = state.copy(isGrabbingError = false)
    }

}