package com.example.mp3downloader.presentation.home_screen

import android.Manifest
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mp3downloader.presentation.common.TextFieldItem
import com.example.mp3downloader.ui.theme.LightGray
import com.example.mp3downloader.ui.theme.Purple
import com.example.mp3downloader.ui.theme.Typography
import java.net.URLDecoder

@Composable
fun HomeScreen(
    context: Context = LocalContext.current,
    viewModel: HomeViewModel = hiltViewModel()
) {

    val state = viewModel.state
    val rootPath = LocalContext.current.filesDir.absolutePath

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
        Text(text = "YouTube Link", style = Typography.bodyMedium)
        Spacer(modifier = Modifier.height(8.dp))
        TextFieldItem(
            value = state.youtubeLink,
            onValueChange = { viewModel.onEvent(HomeScreenEvents.OnYoutubeLinkChange(it)) },
            onDeleteClick = { viewModel.onEvent(HomeScreenEvents.OnYoutubeLinkChange("")) })
        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Destination Folder", style = Typography.bodyMedium)
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .padding(top = 8.dp)
                .clickable {
                    destinationPicker.launch(Uri.parse("root"))

                },
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(width = 1.dp, color = Color.White),
            colors = CardDefaults.cardColors(containerColor = LightGray)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    Icons.Default.Folder,
                    contentDescription = null,
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (state.destinationFolder.toString()
                            .isEmpty()
                    ) "Select destination" else getHumanReadableUri(
                        state.destinationFolder
                    ),
                    style = Typography.bodyMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        Spacer(modifier = Modifier.height(4.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                Icons.Default.Info,
                contentDescription = null,
                modifier = Modifier.size(16.dp),
                tint = Color.Gray
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Where you want to save the MP3",
                color = Color.Gray,
                style = Typography.bodyMedium,
                fontSize = 12.sp
            )
        }
        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                externalStoragePermissionResultLauncher.launch(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            },
            modifier = Modifier
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Purple,
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(8.dp),
        ) {
            Text(text = "Download", modifier = Modifier.padding(16.dp))
        }
    }
}

fun getHumanReadableUri(uri: Uri): String {
    val decodedPath = URLDecoder.decode(uri.path, "UTF-8")
    val directoryPath = decodedPath.substringAfterLast("/")
    return directoryPath
}
