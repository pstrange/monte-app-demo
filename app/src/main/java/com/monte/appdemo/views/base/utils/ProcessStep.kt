package com.monte.appdemo.views.base.utils

@Suppress("UNCHECKED_CAST")
sealed class ProcessStep<out T> {
    //carga del proceso base
    data class Loading(val progress: Any? = null): ProcessStep<Nothing>()
    //error en el proceso base
    data class Error(val message: String, val code: Int? = -1, val show: Boolean = true): ProcessStep<Nothing>()
    //finalización del proceso base
    data class Finished<out T>(val data: T? = null): ProcessStep<T>()
}