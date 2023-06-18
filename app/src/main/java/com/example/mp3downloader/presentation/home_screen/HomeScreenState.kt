package com.example.mp3downloader.presentation.home_screen

import android.net.Uri
import com.example.mp3downloader.data.model.VideoInfo

data class HomeScreenState(
    val youtubeLink: String = "https://www.youtube.com/watch?v=rru2passumI",
    val destinationFolder: Uri = Uri.EMPTY,

    val step: HomeScreenStep = HomeScreenStep.INITIAL,

    val toastErrorMessage: String? = null,
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
