package com.kursales.core.result

import com.kursales.core.R
import com.kursales.core.error.ConnectionException
import com.kursales.core.error.EmptyResponseException
import com.kursales.core.text.Text
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

sealed class ResponseResult<out T> {

    sealed class Success<T> : ResponseResult<T>(), HttpResponse {

        abstract val value: T

        override fun toString() = "Success($value)"

        data class HttpResponse<T>(
            override val value: T,
            override val statusCode: Int,
            override val statusMessage: String? = null,
            override val url: String? = null,
            override val responseHeaders: Map<String, List<String>>
        ) : Success<T>()

        data class Empty(
            override val statusCode: Int,
            override val statusMessage: String? = null,
            override val url: String? = null,
            override val responseHeaders: Map<String, List<String>>
        ) : Success<Nothing>() {
            override val value: Nothing
                get() = throw EmptyResponseException(
                    text = Text.Resource(R.string.error_empty_response),
                    details = Text.Plain("code:$statusCode url:$url"),
                    message = "No value. ResponseResult.Success.Empty. code:$statusCode url:$url"
                )
        }
    }


    sealed class Failure<E : Throwable>(open val error: E) : ResponseResult<Nothing>() {

        override fun toString() = "Failure($error)"

        class Error(override val error: Throwable) : Failure<Throwable>(error)

        class ConnectionError(override val error: Throwable) : Failure<Throwable>(error)

    }
}


fun <T> ResponseResult<T>.isSuccess(): Boolean {
    return this is ResponseResult.Success
}

fun <T> ResponseResult<T>.asSuccess(): ResponseResult.Success<T> {
    return this as ResponseResult.Success<T>
}

@OptIn(ExperimentalContracts::class)
fun <T> ResponseResult<T>.isFailure(): Boolean {
    contract {
        returns(true) implies (this@isFailure is ResponseResult.Failure<*>)
    }
    return this is ResponseResult.Failure<*>
}

fun <T> ResponseResult<T>.asFailure(): ResponseResult.Failure<*> {
    return this as ResponseResult.Failure<*>
}

fun <T> ResponseResult<T>.getOrThrow(): T = when (this) {
    is ResponseResult.Success -> value
    is ResponseResult.Failure.Error -> throw error
    is ResponseResult.Failure.ConnectionError -> throw ConnectionException(
        text = Text.Resource(R.string.error_connection),
        cause = this.error
    )
}

fun ResponseResult<Unit>.getOrThrow() = when (this) {
    is ResponseResult.Success -> Unit
    is ResponseResult.Failure.Error -> throw error
    is ResponseResult.Failure.ConnectionError -> throw ConnectionException(
        text = Text.Resource(R.string.error_connection), cause = this.error
    )
}

fun <T> ResponseResult<T>.getOrNull(): T? = when (this) {
    is ResponseResult.Success -> value
    else -> null
}

fun <T> ResponseResult<T>.asHttpResponseOrThrow(): HttpResponse {
    return (this as? HttpResponse) ?: error("can't cast ResponseResult to HttpResponse")
}

@JvmName("asResultOf")
fun <T> ResponseResult<T>.asResultOf(): ResultOf<T> = resultOf { getOrThrow() }

@JvmName("asResultOfUnit")
fun ResponseResult<Unit>.asResultOf(): ResultOf<Unit> = resultOf { getOrThrow() }


