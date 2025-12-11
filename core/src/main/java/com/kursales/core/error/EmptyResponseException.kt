package com.kursales.core.error

import com.kursales.core.text.Text

class EmptyResponseException(
    override val text: Text.Parcelable,
    override val details: Text.Parcelable? = null,
    message: String? = null,
    cause: Throwable? = null,
) : CountriesException(text, details, message, cause)