package com.monte.data.utils

interface InputMapper<in IN_A, out OUT_A> {
    fun mapIn(from: IN_A): OUT_A
}

interface OutputMapper<in IN_A, out OUT_A> {
    fun mapOut(from: IN_A): OUT_A
}