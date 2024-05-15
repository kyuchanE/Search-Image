package com.example.domain.model

sealed class NetworkResult<T>(var data: T? = null, var code: String? = null, var message: String? = null) {
    data class Success<T> constructor(val value: T): NetworkResult<T>(value)

    class Error<T> @JvmOverloads constructor(
        var errorcode: String? = null,
        var msg: String? = null,
        var exception: Throwable? = null
    ): NetworkResult<T>(null, errorcode, msg)

    class Loading<T>: NetworkResult<T>(null, null, null)
}
