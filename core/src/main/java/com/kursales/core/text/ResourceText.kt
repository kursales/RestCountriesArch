package com.kursales.core.text

import android.content.Context
import androidx.annotation.StringRes
import kotlinx.parcelize.Parcelize

@Parcelize
data class ResourceText(@StringRes val resourceId: Int) : Text.Parcelable {
    override fun get(context: Context): CharSequence = context.getText(resourceId)
}