package com.example.mp3downloader.data.model

import com.squareup.moshi.Json

data class DownloadProgressInfo(
    @field:Json(name = "downloaded_bytes")
    val downloadedBytes: Long,
    @field:Json(name = "total_bytes_estimate")
    val totalBytesEstimate: Long,
    val eta: Long,
    val speed: Double
){
    val percentComplete : Float
        get() = (downloadedBytes*1.0/totalBytesEstimate).toFloat()
}