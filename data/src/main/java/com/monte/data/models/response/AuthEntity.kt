package com.monte.data.models.response

import com.google.gson.annotations.SerializedName

data class AuthEntity(
	@SerializedName("token")
	val token: String,
	@SerializedName("user")
	val user: UserEntity
)
