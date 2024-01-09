package com.monte.domain.usecases

import com.monte.domain.models.common.Outcome
import com.monte.domain.models.common.StepResult
import com.monte.domain.repository.PreferencesRepository
import com.monte.domain.utils.asOutcome

class SessionUseCase(
    private val preferences: PreferencesRepository
) {

    suspend operator fun invoke() : Outcome<StepResult> {
        validateSessionToken()?.let {
            return it.asOutcome()
        }
        return StepResult.Continue().asOutcome()
    }

    private suspend fun validateSessionToken() : StepResult? {
        return try {
            if(preferences.hasActiveSession().value == true){
                StepResult.RequireSession()
            }else
                StepResult.RequireSession()
        }catch (ex: Exception){
            ex.printStackTrace()
            preferences.setHasActiveSession(false)
            StepResult.RequireSession()
        }
    }

}