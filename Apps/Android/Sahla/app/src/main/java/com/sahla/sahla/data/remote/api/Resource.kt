package com.sahla.sahla.data.remote.api

enum class Status { LOADING, SUCCESS, ERROR, FAILED, EMPTY }
sealed class Resource<T>(
    val data: T? = null,
    val apiStatus: Status,
    val message: String? = null
) {
    class Success<T>(data: T? = null, message: String? = null) :
        Resource<T>(data = data, apiStatus = Status.SUCCESS, message = message)

    class Loading<T> : Resource<T>(apiStatus = Status.LOADING)
    class Error<T>(message: String?) : Resource<T>(apiStatus = Status.ERROR, message = message)
    class Empty<T>(message: String?) : Resource<T>(message = message, apiStatus = Status.EMPTY)
    class Failed<T>(message: String? = null) :
        Resource<T>(apiStatus = Status.FAILED, message = message)
}