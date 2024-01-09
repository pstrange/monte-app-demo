package com.monte.domain.models.response

data class Auth(
	val token: String,
	val user: User
)
