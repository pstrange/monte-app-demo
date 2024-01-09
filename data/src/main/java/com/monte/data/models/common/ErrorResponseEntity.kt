package com.monte.data.models.common

import com.google.gson.annotations.SerializedName

data class ErrorResponseEntity(
    @SerializedName("error")
    val error: ErrorEntity?
)

data class ErrorEntity(
    @SerializedName("statusCode")
    val status: Int?,
    @SerializedName("error")
    val error: String?,
    @SerializedName("message")
    val message: String?,
)