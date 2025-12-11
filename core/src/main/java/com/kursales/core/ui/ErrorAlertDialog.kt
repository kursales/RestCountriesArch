package com.kursales.core.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.kursales.core.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ErrorAlertDialog(
    text: String,
    onDismiss: () -> Unit,
) {
    BasicAlertDialog(
        onDismissRequest = {}
    ) {
        Card() {
            Column(modifier  = Modifier.padding(16.dp)) {
                Text(text = text)
                Text(
                    modifier = Modifier
                        .padding(top = 12.dp)
                        .align(Alignment.End)
                        .clickable {
                            onDismiss()
                        },
                    color = MaterialTheme.colorScheme.primary,
                    text = stringResource(R.string.ok),
                )
            }
        }

    }
}