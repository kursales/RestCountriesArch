package com.kursales.core.text

import android.content.Context
import kotlinx.parcelize.Parcelize

@Parcelize
data class PlainText(val text: CharSequence) : Text.Parcelable {
    override fun get(context: Context): CharSequence = text
}