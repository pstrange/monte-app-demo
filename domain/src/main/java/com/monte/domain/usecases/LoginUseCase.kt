package com.monte.domain.usecases

import com.monte.domain.models.common.Outcome
import com.monte.domain.models.request.Login
import com.monte.domain.models.response.Auth
import com.monte.domain.repository.AuthRepository
import com.monte.domain.repository.PreferencesRepository

class LoginUseCase(
	private val hostApi: AuthRepository,
	private val preferences: PreferencesRepository
) {
	suspend operator fun invoke(username: String, password: String) : Outcome<Auth> {
		val result = hostApi.login(
			Login(
				username = username,
				password = password)
		)

		when(result){
			is Outcome.Success -> {
				preferences.setAuthToken(result.value?.token.toString())
				preferences.setHasActiveSession(true)
			}
			else -> Unit
		}
		return result
	}
}