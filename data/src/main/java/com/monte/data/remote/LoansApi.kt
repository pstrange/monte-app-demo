package com.monte.data.remote

import com.monte.data.models.request.CalculateEntity
import com.monte.data.models.response.PriceEntity
import com.monte.data.models.response.ResponseEntity
import com.monte.data.models.response.CalcResultEntity
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET

interface LoansApi {

    @GET("api/loans/catalogue")
    suspend fun getCatalogue(
    ) : Response<ResponseEntity<List<PriceEntity>>>

    @GET("api/loans/calculator")
    suspend fun calculate(
        @Body body: CalculateEntity?
    ) : Response<ResponseEntity<CalcResultEntity>>

}