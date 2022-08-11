package com.mlallemant.feature_auth.data.repository

import com.mlallemant.core.data.Transaction.dbQuery
import com.mlallemant.feature_auth.domain.model.User
import com.mlallemant.feature_auth.domain.model.UserTable
import com.mlallemant.feature_auth.domain.repository.AuthRepository

class AuthRepositoryImpl : AuthRepository {

    override suspend fun getUserByEmail(email: String): User? = dbQuery {
        User.find {
            UserTable.email eq email
        }.singleOrNull()
    }

    override suspend fun registerUser(email: String, passwordHash: String): Unit = dbQuery {
        User.new {
            this.email = email
            this.password = password
        }
    }

}