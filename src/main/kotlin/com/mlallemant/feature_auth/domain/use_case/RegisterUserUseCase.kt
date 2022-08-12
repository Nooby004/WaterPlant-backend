package com.mlallemant.feature_auth.domain.use_case

import com.mlallemant.feature_auth.domain.model.RegisterResponse
import com.mlallemant.feature_auth.domain.repository.AuthRepository

class RegisterUserUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): RegisterResponse =
        repository.registerUser(email, password)
}
