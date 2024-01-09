package com.monte.data.models.response

import com.google.gson.annotations.SerializedName

data class PriceEntity(
	@SerializedName("_id")
	val id: String,
	@SerializedName("identifier")
	val identifier: String,
	@SerializedName("name")
	val name: String,
	@SerializedName("price")
	val price: Double
)