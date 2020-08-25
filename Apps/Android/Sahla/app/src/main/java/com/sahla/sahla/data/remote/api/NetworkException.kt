package com.sahla.sahla.data.remote.api

import com.google.gson.Gson
import com.sahla.sahla.data.remote.response.BaseResponse
import retrofit2.HttpException

/**
 * this method will extract errors from the http exception response body
 * @return the error object from ErrorList array
 */
class NetworkException<T> {
    fun extractErrorBody(exception: HttpException): String {
        val response: String? = exception.response()?.errorBody()?.string().toString()
        val objectResponse: BaseResponse<T>? =
            Gson().fromJson(response, BaseResponse<T>()::class.java)
        return objectResponse?.errors() ?: "Server Error"
    }
}