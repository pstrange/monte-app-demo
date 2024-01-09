package com.monte.domain.repository

import com.monte.domain.models.common.Outcome
import com.monte.domain.models.request.Login
import com.monte.domain.models.response.Auth

interface AuthRepository {
	suspend fun login(body: Login): Outcome<Auth>
}