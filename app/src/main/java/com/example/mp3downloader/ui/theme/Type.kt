package com.example.mp3downloader.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.mp3downloader.R

// Set of Material typography styles to start with

val plus_jakarta_sans = FontFamily(
    Font(R.font.plus_jakarta_sans_semibold, style = FontStyle.Normal),
    Font(R.font.plus_jakarta_sans_bold, style = FontStyle.Normal)
)

val Typography = Typography(
    bodyMedium = TextStyle(
        fontFamily = plus_jakarta_sans,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
    ),
    titleMedium = TextStyle(
        fontFamily = plus_jakarta_sans,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
    ),
    labelSmall = TextStyle(
        fontFamily = plus_jakarta_sans,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
    )
)