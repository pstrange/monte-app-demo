package com.monte.data.models.request

import com.google.gson.annotations.SerializedName

data class CalculateEntity(
	@SerializedName("identifier")
	val identifier: String,
	@SerializedName("weight")
	val weight: Int
)