package com.example.mp3downloader.presentation.common.step_content

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.mp3downloader.ui.theme.Typography

@Composable
fun SavingStepContent() {
    Text(text = "Saving...", style = Typography.bodyMedium)
}