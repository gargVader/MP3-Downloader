package com.example.mp3downloader.presentation.home_screen

import android.net.Uri
import com.example.mp3downloader.data.model.VideoInfo

data class HomeScreenState(
    val youtubeLink: String = "https://www.youtube.com/watch?v=5Eqb_-j3FDA",
    val destinationFolder: Uri = Uri.EMPTY,

    val step: HomeScreenStep = HomeScreenStep.INITIAL,

    val isGrabbingError: Boolean = false,
    val isDownloadingError: Boolean = false,
    val isConvertingError: Boolean = false,
    val isSavingError: Boolean = false,

    val videoInfo: VideoInfo? = null
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
