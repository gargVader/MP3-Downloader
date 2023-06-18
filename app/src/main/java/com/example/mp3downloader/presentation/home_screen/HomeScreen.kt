package com.example.mp3downloader.presentation.home_screen

import android.Manifest
import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mp3downloader.presentation.common.DestinationFolderItem
import com.example.mp3downloader.presentation.common.DownloadButton
import com.example.mp3downloader.presentation.common.TextFieldItem
import com.example.mp3downloader.presentation.common.VideoInfoItem
import com.example.mp3downloader.presentation.common.step_content.ConvertingStepContent
import com.example.mp3downloader.presentation.common.step_content.DownloadingStepContent
import com.example.mp3downloader.presentation.common.step_content.FailureStepContent
import com.example.mp3downloader.presentation.common.step_content.SavingStepContent
import com.example.mp3downloader.presentation.common.step_content.SuccessStepContent
import com.example.mp3downloader.ui.theme.Typography
import java.net.URLDecoder

@Composable
fun HomeScreen(
    context: Context = LocalContext.current,
    viewModel: HomeViewModel = hiltViewModel()
) {

    val state = viewModel.state
    val rootPath = LocalContext.current.filesDir.absolutePath

    LaunchedEffect(state) {
        if (state.isGrabbingError) {
            Toast.makeText(context, "Failed to grab video info", Toast.LENGTH_SHORT).show()
            viewModel.grabbingErrorMessageShown()
        }
    }

    val destinationPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocumentTree(),
        onResult = {
            if (it != null) {
                viewModel.onEvent(HomeScreenEvents.OnDestinationFolderChange(it))
            }
        }
    )
    val externalStoragePermissionResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            Log.d("Girish", "HomeScreen: $isGranted")
            viewModel.onEvent(HomeScreenEvents.OnVideoGrabInfo(rootPath = rootPath))
        }
    )


    Column() {
        if (state.step in listOf(
                HomeScreenStep.INITIAL,
                HomeScreenStep.GRABBING
            )
        ) {
            Text(text = "YouTube Link", style = Typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))
            TextFieldItem(
                value = state.youtubeLink,
                enabled = state.step == HomeScreenStep.INITIAL,
                onValueChange = { viewModel.onEvent(HomeScreenEvents.OnYoutubeLinkChange(it)) },
                onDeleteClick = { viewModel.onEvent(HomeScreenEvents.OnYoutubeLinkChange("")) })
            Spacer(modifier = Modifier.height(16.dp))


            Spacer(modifier = Modifier.height(24.dp))
            DestinationFolderItem(
                onClick = { destinationPicker.launch(Uri.parse("root")) },
                enabled = state.step == HomeScreenStep.INITIAL,
                displayPath = if (state.destinationFolder.toString()
                        .isEmpty()
                ) "Select destination" else getHumanReadableUri(
                    state.destinationFolder
                )
            )

            DownloadButton(step = state.step) {
                if (state.youtubeLink.isBlank()) {
                    Toast.makeText(context, "Link is empty", Toast.LENGTH_SHORT).show()
                } else {
                    externalStoragePermissionResultLauncher.launch(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    )
                }
            }
        } else {
            state.videoInfo?.let { videoInfo ->
                Column {
                    VideoInfoItem(videoInfo = videoInfo)
                    Spacer(modifier = Modifier.height(8.dp))
                    when (state.step) {
                        HomeScreenStep.DOWNLOADING -> {
                            DownloadingStepContent()
                        }

                        HomeScreenStep.CONVERTING -> {
                            ConvertingStepContent()
                        }

                        HomeScreenStep.SAVING -> {
                            SavingStepContent()
                        }

                        HomeScreenStep.SUCCESS -> {
                            SuccessStepContent()
                        }

                        HomeScreenStep.FAILED -> {
                            FailureStepContent()
                        }

                        else -> {

                        }
                    }
                }
            }

        }
    }
}

fun getHumanReadableUri(uri: Uri): String {
    val decodedPath = URLDecoder.decode(uri.path, "UTF-8")
    val directoryPath = decodedPath.substringAfterLast("/")
    return directoryPath
}
