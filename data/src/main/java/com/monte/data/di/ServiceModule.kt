package com.monte.data.di

import com.monte.data.BuildConfig
import com.monte.data.remote.*
import com.monte.data.utils.AuthInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val CONNECT_TIMEOUT = 30L
private const val WRITE_TIMEOUT = 30L
private const val READ_TIMEOUT = 30L

val serviceModule = module {

    single {
        getRetrofitWithAuth().create(AuthApi::class.java)
    }

    single {
        getRetrofitWithAuth().create(LoansApi::class.java)
    }

}

private fun getRetrofitWithAuth() =
    Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BuildConfig.HOST_API)
        .client(getClient(AuthInterceptor()))
        .build()

private fun getClient(interceptor: Interceptor? = null) =
    OkHttpClient.Builder().apply {
        connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
        writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
        readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
        retryOnConnectionFailure(true)
        interceptor?.let { validInterceptor ->
            addInterceptor(validInterceptor)
        }
        addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
    }.build()