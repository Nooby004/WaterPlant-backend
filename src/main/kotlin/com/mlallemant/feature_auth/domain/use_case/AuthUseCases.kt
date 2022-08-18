package com.mlallemant.feature_auth.domain.use_case

data class AuthUseCases(
    val registerUserUseCase: RegisterUserUseCase,
    val getUserByEmailUseCase: GetUserByEmailUseCase,
    val getAllUsers: GetAllUsersUseCase
)
