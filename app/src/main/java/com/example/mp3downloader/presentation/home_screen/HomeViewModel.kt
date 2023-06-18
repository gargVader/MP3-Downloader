package com.example.mp3downloader.presentation.home_screen

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {
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
                viewModelScope.launch {
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

                    val internalStorageFilePath = "${event.rootPath + File.separator}output.mp3"
                    val destinationUri = state.destinationFolder


                    Log.d("Girish", "onEvent: ${state.destinationFolder}")
                    Log.d("Girish", "onEvent: ${state.destinationFolder.path}")
                    Log.d("Girish", "onEvent: $internalStorageFilePath")

                }
            }
        }
    }

}