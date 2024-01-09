package com.monte.data.mappers

import com.monte.data.models.request.CalculateEntity
import com.monte.data.models.response.CalcResultEntity
import com.monte.data.utils.InputMapper
import com.monte.data.utils.OutputMapper
import com.monte.domain.models.request.Calculate
import com.monte.domain.models.response.CalcResult

class CalculatorMapper :
	InputMapper<Calculate, CalculateEntity>,
	OutputMapper<CalcResultEntity, CalcResult> {

	override fun mapIn(from: Calculate): CalculateEntity =
		CalculateEntity(
			identifier = from.identifier,
			weight = from.weight
		)

	override fun mapOut(from: CalcResultEntity): CalcResult =
		CalcResult(
			total = from.total
		)

}