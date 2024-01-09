package com.monte.data.remote

import com.monte.data.models.request.LoginEntity
import com.monte.data.models.response.AuthEntity
import com.monte.data.models.response.ResponseEntity
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("api/auth/login")
    suspend fun login(
        @Body body: LoginEntity?
    ) : Response<ResponseEntity<AuthEntity>>

}