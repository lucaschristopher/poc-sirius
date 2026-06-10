package com.example.pocsirius.feature.auth.data.api

import com.example.pocsirius.feature.auth.data.model.request.LoginRequest
import com.example.pocsirius.feature.auth.data.model.response.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse
}