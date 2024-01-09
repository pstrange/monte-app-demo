package com.monte.data.repository

import com.monte.data.mappers.CalculatorMapper
import com.monte.data.mappers.CatalogueMapper
import com.monte.data.models.response.CalcResultEntity
import com.monte.data.models.response.PriceEntity
import com.monte.data.models.response.ResponseEntity
import com.monte.data.remote.LoansApi
import com.monte.data.utils.ApiDataSource
import com.monte.domain.models.request.Calculate
import com.monte.domain.models.response.CalcResult
import com.monte.domain.models.response.Price
import com.monte.domain.repository.LoansRepository
import com.monte.domain.utils.asOutcome

class LoansRepositoryImpl(
	private val apiLoans: LoansApi,
	private val catalogueMapper: CatalogueMapper,
	private val calculatorMapper: CalculatorMapper
) : LoansRepository {

	override suspend fun getCatalogue() =
		object : ApiDataSource<ResponseEntity<List<PriceEntity>>, List<Price>>() {
			override suspend fun consumeService() =
				apiLoans.getCatalogue()
			override fun getOutcome(body: ResponseEntity<List<PriceEntity>>) =
				catalogueMapper.mapOut(body.result).asOutcome()
		}.consume()

	override suspend fun calculate(body: Calculate) =
		object : ApiDataSource<ResponseEntity<CalcResultEntity>, CalcResult>() {
			override suspend fun consumeService() =
				apiLoans.calculate(calculatorMapper.mapIn(body))
			override fun getOutcome(body: ResponseEntity<CalcResultEntity>) =
				calculatorMapper.mapOut(body.result).asOutcome()
		}.consume()

}