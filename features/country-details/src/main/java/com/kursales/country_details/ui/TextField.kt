package com.kursales.country_details.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TextField(
    modifier: Modifier = Modifier,
    text: String,
) {
    Card(modifier = modifier) {
        Text(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            text = text,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}