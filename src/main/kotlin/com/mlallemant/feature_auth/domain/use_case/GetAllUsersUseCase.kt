package com.mlallemant.feature_auth.domain.use_case

import com.mlallemant.feature_auth.domain.repository.AuthRepository

class GetAllUsersUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke() = repository.getAllUsers()
}