package com.example.mp3downloader.data.model

import com.squareup.moshi.Json
import java.text.DecimalFormat

data class VideoInfo(
    @field:Json(name = "thumbnail")
    val thumbnail: String,
    @field:Json(name = "title")
    val title: String,
    @field:Json(name = "like_count")
    val like_count: Long,
    @field:Json(name = "view_count")
    val view_count: Long
){
    val simplifiedTitle : String
        get() {
            return title.replace(Regex("[^\\w\\s]"), "")
        }
}

fun Long.simplifyAmount(): String {
    var am: Float
    val df = DecimalFormat("##.##")

    var simplify = ""

    if (this / 1000000000 >= 1) {
        am = this.toFloat() / 1000000000
        simplify = df.format(am) + "B"
    } else if (this / 1000000 >= 1) {
        am = this.toFloat() / 1000000
        simplify = df.format(am) + "M"
    } else if (this / 1000 >= 1) {
        am = this.toFloat() / 1000
        simplify = df.format(am) + "K"
    } else {
        simplify = this.toString()
    }

    return simplify
}
