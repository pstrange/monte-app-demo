package com.monte.data.models.response

import com.google.gson.annotations.SerializedName

data class UserEntity(
	@SerializedName("_id")
	val id: String,
	@SerializedName("username")
	val username: String,
	@SerializedName("role")
	val role: String
)