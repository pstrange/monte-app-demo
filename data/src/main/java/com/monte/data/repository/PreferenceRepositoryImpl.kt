package com.monte.data.repository

import com.monte.data.local.Preference_AppConfigs
import com.monte.domain.models.common.Outcome
import com.monte.domain.repository.PreferencesRepository
import com.monte.domain.utils.asOutcome

class PreferenceRepositoryImpl(
    private val preferences: Preference_AppConfigs
) : PreferencesRepository {

    override suspend fun setAuthToken(authToken: String) =
        preferences.putAuthToken(authToken).asOutcome()

    override suspend fun setHasActiveSession(isActive: Boolean) =
        preferences.putActiveSession(isActive).asOutcome()

    override suspend fun hasActiveSession(): Outcome<Boolean> =
        preferences.activeSession.asOutcome()
}