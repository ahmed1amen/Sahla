package com.sahla.sahla.data.remote.api

import com.sahla.sahla.data.local.PreferenceHelper
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(private val preferenceHelper: PreferenceHelper) :
    Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request().newBuilder()
            .header("Content-Type", "application/json")
            .header("Accept-Language", preferenceHelper.selectedLanguage())
        return chain.proceed(request.build())
    }
}