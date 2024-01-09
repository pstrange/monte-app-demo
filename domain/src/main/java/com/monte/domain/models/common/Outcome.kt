package com.monte.domain.models.common

sealed class Outcome<out T> {
    data class Success<out T>(val data: T): Outcome<T>()
    data class Error(val message: String, val code: Int? = -1, val type: Type = Type.GENERIC): Outcome<Nothing>(){
        enum class Type{ GENERIC, FORMAT, CONNECTION }
    }
    object Completed: Outcome<Nothing>()

    //se obtiene el resultado directamente
    val value: T? get() = if(this is Success) data else null
}