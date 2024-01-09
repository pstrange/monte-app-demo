package com.monte.data.utils

import com.monte.data.local.PreferenceComponent_PrefsComponent
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val token = if(PreferenceComponent_PrefsComponent.getInstance().AppConfigs().activeSession)
             PreferenceComponent_PrefsComponent.getInstance().AppConfigs().authToken else ""

        request = request.newBuilder()
            .addHeader("Authorization", "Bearer $token")
            .build()
        return chain.proceed(request)
    }

}