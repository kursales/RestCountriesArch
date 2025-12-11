package com.kursales.core.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.kursales.core.R

@Composable
fun NoConnectionDialog(
    onDismiss: () -> Unit,
    onRefresh: () -> Unit,
) {
    AlertDialog(
        title = { Text(stringResource(R.string.no_connection)) },
        text = { Text(stringResource(R.string.please_make_sure_yor_connection_is_fine)) },
        onDismissRequest = {
            onDismiss()
        },
        confirmButton = {
            Text(
                modifier = Modifier
                    .padding(top = 12.dp)
                    .clickable {
                    onRefresh()
                },
                text = "Refresh",
                color = MaterialTheme.colorScheme.primary,
            )

        }
    )
}