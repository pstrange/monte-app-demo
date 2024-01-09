package com.monte.data.models.response

import com.google.gson.annotations.SerializedName

data class ResponseEntity<T>(
	@SerializedName("message")
	val message: String,
	@SerializedName("result")
	val result: T
)