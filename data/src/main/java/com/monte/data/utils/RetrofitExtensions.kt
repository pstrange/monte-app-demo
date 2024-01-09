package com.monte.data.utils

import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.security.cert.CertPathValidatorException

fun handleThrowable(t: Throwable): String = when(t){
    is SocketTimeoutException, is ConnectException, is UnknownHostException,
    is CertPathValidatorException -> "Intenta de nuevo mas tarde"
    else -> "Error desconocido"
}