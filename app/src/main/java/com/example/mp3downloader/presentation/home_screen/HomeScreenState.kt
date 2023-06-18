package com.example.mp3downloader.presentation.home_screen

import android.net.Uri

data class HomeScreenState(
    val youtubeLink: String = "",
    val destinationFolder: Uri = Uri.EMPTY
)