package com.sahla.sahla.data.remote.response

import com.google.gson.annotations.SerializedName

data class BaseResponse<T>(
    @SerializedName("IsSuccess")
    val isSuccess: Boolean? = null,
    @SerializedName("SuccessMessage")
    val successMessage: String? = null,
    @SerializedName("StatusCode")
    val statusCode: Int? = null,
    @SerializedName("Data")
    var data: T? = null,
    @SerializedName("ErrorList")
    val errorList: List<String>? = null
) {
    fun errors(): String {
        val error = StringBuilder()
        errorList?.forEach { e -> error.append("$e\n") }
        return error.toString()
    }
}