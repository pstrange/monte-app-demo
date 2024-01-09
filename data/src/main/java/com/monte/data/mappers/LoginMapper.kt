package com.monte.data.mappers

import com.monte.data.models.request.LoginEntity
import com.monte.data.models.response.AuthEntity
import com.monte.data.utils.InputMapper
import com.monte.data.utils.OutputMapper
import com.monte.domain.models.request.Login
import com.monte.domain.models.response.Auth
import com.monte.domain.models.response.User

class LoginMapper :
	InputMapper<Login, LoginEntity>,
	OutputMapper<AuthEntity, Auth> {

	override fun mapIn(from: Login): LoginEntity =
		LoginEntity(
			username = from.username,
			password = from.password
		)

	override fun mapOut(from: AuthEntity): Auth =
		Auth(
			token = from.token,
			user = User(
				id = from.user.id,
				username = from.user.username,
				role = from.user.role
			)
		)

}