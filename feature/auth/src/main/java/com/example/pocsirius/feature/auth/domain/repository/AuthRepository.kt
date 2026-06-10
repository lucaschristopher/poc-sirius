package com.example.pocsirius.feature.auth.domain.repository

interface AuthRepository {
    suspend fun login(email: String, password: String)
}