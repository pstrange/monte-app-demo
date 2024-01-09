package com.monte.data.models.common

import com.google.gson.annotations.SerializedName

data class ErrorListResponseEntity(
    @SerializedName("statusCode")
    val status: Int?,
    @SerializedName("message")
    val message: List<MessagesEntity>?
)

data class MessagesEntity(
    @SerializedName("messages")
    val messages: List<MessageEntity>?
)

data class MessageEntity(
    @SerializedName("id")
    val id: String?,
    @SerializedName("message")
    val message: String?
)