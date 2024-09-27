package com.venunalla.weatherapp.ui.screens.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.venunalla.weatherapp.R

@Composable
fun CircularProgressDialog(showDialog: Boolean, onDismiss: () -> Unit) {
    if (showDialog) {
        Dialog(
            onDismissRequest = {
                onDismiss()
            },
            DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false),
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(100.dp)
                    .background(White, shape = RoundedCornerShape(8.dp))
            ) {
                CircularProgressIndicator()
            }
        }
    }
}

@Composable
fun ErrorDialog(showDialog: Boolean = true, onDismiss: () -> Unit, errorMessage: String) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { onDismiss() },
            title = { Text(text = errorMessage, style = MaterialTheme.typography.headlineMedium) },
            text = {
                Text(
                    text = stringResource(R.string.please_try_with_other_city_name),
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            confirmButton = {
                Button(onClick = { onDismiss() }) {
                    Text(
                        text = stringResource(R.string.try_again),
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            },
            containerColor = colorResource(R.color.white)
        )
    }
}