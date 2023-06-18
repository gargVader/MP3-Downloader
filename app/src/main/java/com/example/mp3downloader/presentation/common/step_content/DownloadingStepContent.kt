package com.example.mp3downloader.presentation.common.step_content

import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mp3downloader.ui.theme.Blue

@Composable
fun DownloadingStepContent(
    percentComplete: Float = 0f
) {
    val animatedProgress by animateFloatAsState(
        targetValue = percentComplete,
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
    )
    Text(text = "Downloading...")
    LinearProgressIndicator(
        progress = animatedProgress,
        modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
        color = Blue
    )
}