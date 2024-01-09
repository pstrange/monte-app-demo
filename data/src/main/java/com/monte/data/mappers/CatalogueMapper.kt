package com.monte.data.mappers

import com.monte.data.models.response.PriceEntity
import com.monte.data.utils.OutputMapper
import com.monte.domain.models.response.Price

class CatalogueMapper :
	OutputMapper<List<PriceEntity>, List<Price>> {

	override fun mapOut(from: List<PriceEntity>): List<Price> =
		from.map {
			Price(
				id = it.id,
				identifier = it.identifier,
				name = it.name,
				price = it.price
			)
		}

}