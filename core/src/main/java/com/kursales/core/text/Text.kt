@file:Suppress("FunctionName")

package com.kursales.core.text

import android.content.Context
import androidx.annotation.StringRes

/**
 * Wrapper to make it possible to work with plain [CharSequence] and [StringRes] in the same way.
 *
 * ```
 *  // in some place where we can't access Context
 *  val errorMessage = exception.message?.let(Text::Plain) ?: Text.Resource(R.string.unknown_error)
 *  showMessage(errorMessage)
 *
 *  // in Activity, Fragment or View
 *  val messageText = getString(message)
 * ```
 */
interface Text {

    fun get(context: Context): CharSequence

    interface Parcelable : Text, android.os.Parcelable

    companion object {

        fun Plain(text: CharSequence) = PlainText(text)

        fun Resource(@StringRes resourceId: Int) = ResourceText(resourceId)

    }
}
