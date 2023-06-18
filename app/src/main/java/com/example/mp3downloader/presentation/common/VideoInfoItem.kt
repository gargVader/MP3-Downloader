package com.example.mp3downloader.presentation.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.mp3downloader.data.model.VideoInfo
import com.example.mp3downloader.data.model.simplifyAmount
import com.example.mp3downloader.ui.theme.LightGray
import com.example.mp3downloader.ui.theme.Typography

@Composable
fun VideoInfoItem(videoInfo: VideoInfo) {
    Card(
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = LightGray),
        modifier = Modifier.clip(RoundedCornerShape(8.dp))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp)
        ) {
            AsyncImage(
                model = videoInfo.thumbnail,
                contentScale = ContentScale.Inside,
                contentDescription = null,
                modifier = Modifier.clip(RoundedCornerShape(8.dp))
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = videoInfo.title,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = Typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Filled.Visibility,
                    contentDescription = null,
                    modifier = Modifier.padding(end = 8.dp),
                    tint = Color.Gray
                )
                Text(text = "${videoInfo.view_count.simplifyAmount()} Views", color = Color.Gray)

                Icon(
                    Icons.Filled.ThumbUp,
                    contentDescription = null,
                    modifier = Modifier.padding(start = 24.dp, end = 8.dp),
                    tint = Color.Gray
                )
                Text(text = "${videoInfo.like_count.simplifyAmount()} Likes", color = Color.Gray)
            }
        }
    }
}

@Preview
@Composable
fun PreviewVideoInfoItem() {
    VideoInfoItem(
        videoInfo = VideoInfo(
            thumbnail = "https://i.ytimg.com/vi/5Eqb_-j3FDA/maxresdefault.jpg",
            title = "Coke Studio | Season 14 | Pasoori | Ali Sethi x Shae Gill",
            view_count = 592239063,
            like_count = 7200362
        )
    )
}