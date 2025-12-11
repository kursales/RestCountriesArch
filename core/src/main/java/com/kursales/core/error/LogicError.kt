package com.kursales.core.error

import com.kursales.core.R
import com.kursales.core.text.Text

class LogicError(
    message: String,
) : CountriesException(
    text = Text.Resource(R.string.error_logic),
    details = Text.Plain(message),
    message = message,
)