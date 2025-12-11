package com.kursales.core.error

import com.kursales.core.text.Text

class ConnectionException(
    text: Text.Parcelable,
    details: Text.Parcelable? = null,
    cause: Throwable? = null,
) : CountriesException(text = text, details = details, message = null, cause = cause)