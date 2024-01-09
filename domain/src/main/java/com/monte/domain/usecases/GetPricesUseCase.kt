package com.monte.domain.usecases

import com.monte.domain.models.common.Outcome
import com.monte.domain.models.response.Price
import com.monte.domain.repository.LoansRepository

class GetPricesUseCase(
	private val hostApi: LoansRepository
) {
	suspend operator fun invoke() : Outcome<List<Price>> =
		hostApi.getCatalogue()
}