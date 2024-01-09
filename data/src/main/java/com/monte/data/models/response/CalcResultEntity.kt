package com.monte.data.models.response

import com.google.gson.annotations.SerializedName

data class CalcResultEntity(
	@SerializedName("total")
	val total: Double
)