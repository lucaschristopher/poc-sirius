package com.example.pocsirius.feature.auth.domain.usecase

import com.example.pocsirius.core.common.extension.Result

fun interface LoginUseCase {
    suspend operator fun invoke(email: String, password: String): Result<Unit>
}
