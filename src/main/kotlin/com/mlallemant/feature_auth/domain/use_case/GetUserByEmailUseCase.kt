package com.mlallemant.feature_auth.domain.use_case

import com.mlallemant.feature_auth.domain.repository.AuthRepository

class GetUserByEmailUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(email: String) = repository.getUserByEmail(email)
}