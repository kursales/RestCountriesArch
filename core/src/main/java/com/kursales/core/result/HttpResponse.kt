package com.kursales.core.result

interface HttpResponse {

    val statusCode: Int

    val statusMessage: String?

    val url: String?

    val responseHeaders: Map<String, List<String>>

}