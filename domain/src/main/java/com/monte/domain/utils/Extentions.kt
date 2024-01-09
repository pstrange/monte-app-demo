package com.monte.domain.utils

import com.monte.domain.models.common.Outcome
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

fun <T> T?.asOutcome(errorMessage: String? = null): Outcome<T> {
    return if (this != null)
        Outcome.Success(this)
    else
        Outcome.Error(errorMessage ?: "No data")
}

inline fun outcomeCompleted(f: () -> Unit): Outcome<Nothing> {
    f()
    return Outcome.Completed
}

inline fun consume(f: () -> Unit): Boolean {
    f()
    return true
}

fun <T: Any> multipleLetCheck(vararg variables: T?, block: (List<T>) -> Unit): Unit? {
    return if (variables.all { variable -> variable != null }) {
        block(variables.filterNotNull())
    } else {
        null
    }
}

suspend fun <T> withIO(block: suspend CoroutineScope.() -> T) = withContext(Dispatchers.IO, block)
suspend fun <T> withMain(block: suspend CoroutineScope.() -> T) = withContext(Dispatchers.Main, block)