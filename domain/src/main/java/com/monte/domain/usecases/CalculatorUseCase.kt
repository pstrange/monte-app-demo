package com.monte.domain.usecases

import com.monte.domain.models.common.Outcome
import com.monte.domain.models.request.Calculate
import com.monte.domain.models.response.CalcResult
import com.monte.domain.repository.LoansRepository

class CalculatorUseCase(
	private val hostApi: LoansRepository
) {
	suspend operator fun invoke(identifier: String, weight: Int) : Outcome<CalcResult> =
		hostApi.calculate(Calculate(
			identifier = identifier,
			weight = weight
		))
}