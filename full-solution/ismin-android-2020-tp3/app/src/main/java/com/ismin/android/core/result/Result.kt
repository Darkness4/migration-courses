package com.ismin.android.core.result

sealed class Result<out T> {
    data class Success<out T>(val value: T) : Result<T>()
    data class Failure<out T>(val throwable: Throwable) : Result<T>()
    val isSuccess: Boolean
        get() = this is Success
    val isFailure: Boolean
        get() = this is Failure

    /**
     * Get the `value` if `Success` or `null` if `Failure`.
     */
    fun valueOrNull(): T? {
        return if (this is Success) value
        else null
    }

    /**
     * Get the `throwable` if `Failure` or `null` if `Success`.
     */
    fun exceptionOrNull(): Throwable? {
        return if (this is Failure) throwable
        else null
    }

    /**
     * Transform the `value` if `Success`. Else, do nothing.
     */
    inline fun <R> map(transform: (value: T) -> R): Result<R> {
        return when (this) {
            is Success -> Success(transform(value))
            is Failure -> Failure(throwable)
        }
    }

    /**
     * If is `Success`, execute the function.
     */
    inline fun <R> doOnSuccess(
        onSuccess: (value: T) -> R,
    ): R? {
        return if (this is Success) onSuccess(value)
        else null
    }

    /**
     * If is `Failure`, execute the function.
     */
    inline fun <R> doOnFailure(
        onFailure: (exception: Throwable) -> R
    ): R? {
        return if (this is Failure) onFailure(throwable)
        else null
    }

    /**
     * Based on the state, execute the associated function.
     */
    inline fun <R> fold(
        onSuccess: (value: T) -> R,
        onFailure: (exception: Throwable) -> R
    ): R {
        return when (this) {
            is Success -> onSuccess(value)
            is Failure -> onFailure(throwable)
        }
    }
}
