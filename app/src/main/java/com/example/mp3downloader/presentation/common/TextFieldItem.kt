package com.example.mp3downloader.presentation.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.mp3downloader.ui.theme.LightGray

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldItem(
    value: String,
    onValueChange: (value: String) -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: () -> Unit,
    enabled : Boolean = true,
    singleLine: Boolean = true,
) {
    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        enabled = enabled,
        value = value,
        onValueChange = {
            onValueChange(it)
        },
        shape = RoundedCornerShape(8.dp),
        leadingIcon = { Icon(Icons.Default.Link, null) },
        trailingIcon = {
            if (value.isNotEmpty()) {
                IconButton(onClick = {
                    onDeleteClick()
                }) {
                    Icon(
                        imageVector = Icons.Outlined.Close,
                        contentDescription = null
                    )
                }
            }
        },
        singleLine = singleLine,
        colors = TextFieldDefaults.textFieldColors(
            containerColor = LightGray,
        )
    )
}