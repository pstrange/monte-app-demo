package com.monte.domain.repository

import com.monte.domain.models.common.Outcome
import com.monte.domain.models.request.Calculate
import com.monte.domain.models.response.CalcResult
import com.monte.domain.models.response.Price

interface LoansRepository {
	suspend fun getCatalogue(): Outcome<List<Price>>
	suspend fun calculate(body: Calculate): Outcome<CalcResult>
}