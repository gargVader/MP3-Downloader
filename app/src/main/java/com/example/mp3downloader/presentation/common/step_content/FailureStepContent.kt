package com.example.mp3downloader.presentation.common.step_content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mp3downloader.presentation.common.DownloadButton
import com.example.mp3downloader.presentation.home_screen.HomeScreenStep

@Composable
fun FailureStepContent(errorMessage: String, onClick: () -> Unit) {
    Column(modifier = Modifier.fillMaxHeight(), verticalArrangement = Arrangement.SpaceBetween) {
        Text(text = "Failure\n$errorMessage")
        // Ideally DownloadButton shouldn't depend on Step. Since this is just an UI element, we should
        // have an enum for different button types.
        DownloadButton(
            step = HomeScreenStep.FAILED,
            onClick = onClick,
            modifier = Modifier.padding(bottom = 16.dp)
        )
    }
}