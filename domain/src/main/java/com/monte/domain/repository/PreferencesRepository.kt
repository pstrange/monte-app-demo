package com.monte.domain.repository

import com.monte.domain.models.common.Outcome

interface PreferencesRepository {
	suspend fun setHasActiveSession(isActive: Boolean): Outcome<Unit>
	suspend fun hasActiveSession(): Outcome<Boolean>
	suspend fun setAuthToken(authToken: String): Outcome<Unit>
}