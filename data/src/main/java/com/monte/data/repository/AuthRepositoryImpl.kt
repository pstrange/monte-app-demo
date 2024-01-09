package com.monte.data.repository

import com.monte.data.mappers.LoginMapper
import com.monte.data.models.response.AuthEntity
import com.monte.data.models.response.ResponseEntity
import com.monte.data.remote.AuthApi
import com.monte.data.utils.ApiDataSource
import com.monte.domain.models.request.Login
import com.monte.domain.models.response.Auth
import com.monte.domain.repository.AuthRepository
import com.monte.domain.utils.asOutcome

class AuthRepositoryImpl(
	private val apiAuth: AuthApi,
	private val loginMapper: LoginMapper
) : AuthRepository {

	override suspend fun login(body: Login) =
		object : ApiDataSource<ResponseEntity<AuthEntity>, Auth>() {
			override suspend fun consumeService() =
				apiAuth.login(loginMapper.mapIn(body))
			override fun getOutcome(body: ResponseEntity<AuthEntity>) =
				loginMapper.mapOut(body.result).asOutcome()
		}.consume()

}