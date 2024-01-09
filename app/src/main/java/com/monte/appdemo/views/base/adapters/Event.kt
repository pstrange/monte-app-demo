package com.monte.appdemo.views.base.adapters

sealed class Event<out T>{
    data class CREATE<out T>(val item: T? = null) : Event<T>()

    data class CLICK<out T>(val item: T) : Event<T>()
    data class SHARE<out T>(val item: T) : Event<T>()
    data class DELETE<out T>(val item: T) : Event<T>()
    data class EDIT<out T>(val item: T? = null) : Event<T>()
    data class MAP<out T>(val item: T) : Event<T>()
    data class DOWNLOAD<out T>(val item: T) : Event<T>()
}
