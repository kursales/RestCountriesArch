package com.kursales.core.result

import com.kursales.core.error.LogicError
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

sealed class ResultOf<out T> {

    class Success<T>(val value: T) : ResultOf<T>()

    class Failure(val error: Throwable) : ResultOf<Nothing>()


    val isSuccess: Boolean get() = this is Success

    val isFailure: Boolean get() = this is Failure


    companion object {

        fun Success() = Success(Unit)

        fun Failure(message: String) = Failure(LogicError(message))

    }

}


fun <T> ResultOf<T>.getOrThrow(): T = when (this) {
    is ResultOf.Success -> value
    is ResultOf.Failure -> throw error
}

fun <T> ResultOf<T>.getOrNull(): T? = when (this) {
    is ResultOf.Success -> value
    is ResultOf.Failure -> null
}

fun <T> ResultOf<T>.asUnit(): ResultOf<Unit> = when (this) {
    is ResultOf.Failure -> this
    is ResultOf.Success -> ResultOf.Success()
}

@OptIn(ExperimentalContracts::class)
inline fun <R, T> ResultOf<T>.map(transform: (value: T) -> R): ResultOf<R> {
    contract {
        callsInPlace(transform, InvocationKind.AT_MOST_ONCE)
    }
    return when (this) {
        is ResultOf.Success -> ResultOf.Success(transform(value))
        is ResultOf.Failure -> ResultOf.Failure(error)
    }
}

@OptIn(ExperimentalContracts::class)
inline fun <R, T> ResultOf<T>.transformSuccess(transform: (value: T) -> ResultOf<R>): ResultOf<R> {
    contract {
        callsInPlace(transform, InvocationKind.AT_MOST_ONCE)
    }
    return when (this) {
        is ResultOf.Success -> transform(value)
        is ResultOf.Failure -> ResultOf.Failure(error)
    }
}

@OptIn(ExperimentalContracts::class)
inline fun <R, T> ResultOf<T>.transform(transform: (value: ResultOf<T>) -> ResultOf<R>): ResultOf<R> {
    contract {
        callsInPlace(transform, InvocationKind.AT_MOST_ONCE)
    }
    return transform(this)
}

@OptIn(ExperimentalContracts::class)
inline fun <R, T> ResultOf<T>.mapCatching(transform: (value: T) -> R): ResultOf<R> {
    contract {
        callsInPlace(transform, InvocationKind.AT_MOST_ONCE)
    }
    return when (this) {
        is ResultOf.Success -> resultOf { transform(value) }
        is ResultOf.Failure -> ResultOf.Failure(error)
    }
}

@OptIn(ExperimentalContracts::class)
inline fun <T> ResultOf<T>.onFailure(action: (exception: Throwable) -> Unit): ResultOf<T> {
    contract {
        callsInPlace(action, InvocationKind.AT_MOST_ONCE)
    }
    if (this is ResultOf.Failure) action.invoke(error)
    return this
}

@OptIn(ExperimentalContracts::class)
inline fun <T> ResultOf<T>.onSuccess(action: (value: T) -> Unit): ResultOf<T> {
    contract {
        callsInPlace(action, InvocationKind.AT_MOST_ONCE)
    }
    if (this is ResultOf.Success) action.invoke(value)
    return this
}


@OptIn(ExperimentalContracts::class)
inline fun <R, T> ResultOf<T>.fold(
    onSuccess: (value: T) -> R,
    onFailure: (exception: Throwable) -> R,
): R {
    contract {
        callsInPlace(onSuccess, InvocationKind.AT_MOST_ONCE)
        callsInPlace(onFailure, InvocationKind.AT_MOST_ONCE)
    }
    return when (this) {
        is ResultOf.Success -> onSuccess(value)
        is ResultOf.Failure -> onFailure(error)
    }
}

inline fun <T> resultOf(action: () -> T): ResultOf<T> {
    return try {
        ResultOf.Success(action.invoke())
    } catch (e: Throwable) {
        ResultOf.Failure(e)
    }
}
