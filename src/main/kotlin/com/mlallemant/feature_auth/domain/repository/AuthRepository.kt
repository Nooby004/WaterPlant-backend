package com.mlallemant.feature_auth.domain.repository

import com.mlallemant.feature_auth.domain.model.User

interface AuthRepository {

    suspend fun getUserByEmail(email: String): User?

    suspend fun registerUser(email: String, passwordHash: String)

}