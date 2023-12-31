package com.example.mp3downloader.presentation.home_screen

import android.net.Uri
import com.example.mp3downloader.data.model.VideoInfo

data class HomeScreenState(
    val youtubeLink: String = "",
    val destinationFolder: Uri = Uri.EMPTY,

    val step: HomeScreenStep = HomeScreenStep.INITIAL,

    val toastErrorMessage: String? = null,
    // Used to display message in ErrorScreen
    val errorMessage: String = "",

    val videoInfo: VideoInfo? = null,
    val downloadPercentComplete: Float = 0f,
)


enum class HomeScreenStep {
    INITIAL,
    GRABBING,
    DOWNLOADING,
    CONVERTING,
    SAVING,
    SUCCESS,
    FAILED
}
