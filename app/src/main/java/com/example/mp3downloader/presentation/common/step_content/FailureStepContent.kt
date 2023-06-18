package com.example.mp3downloader.presentation.common.step_content

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun FailureStepContent(errorMessage: String) {
    Column {
        Text(text = "Failed")
        Text(text = errorMessage)
    }
}