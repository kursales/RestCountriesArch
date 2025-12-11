package com.kursales.core.error

import com.kursales.core.text.Text
import java.io.IOException

open class CountriesException(
    open val text: Text.Parcelable,
    open val details: Text.Parcelable? = null,
    message: String? = null,
    cause: Throwable? = null,
) : IOException(message, cause), java.io.Serializable