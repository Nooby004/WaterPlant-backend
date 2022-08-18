package com.mlallemant.feature_auth.data.repository

import com.mlallemant.core.data.Transaction.dbQuery
import com.mlallemant.feature_auth.domain.model.RegisterResponse
import com.mlallemant.feature_auth.domain.model.User
import com.mlallemant.feature_auth.domain.model.UserTable
import com.mlallemant.feature_auth.domain.repository.AuthRepository
import java.util.*

class AuthRepositoryImpl : AuthRepository {

    override suspend fun getUserByEmail(email: String): User? = dbQuery {
        User.find {
            UserTable.email eq email
        }.singleOrNull()
    }

    override suspend fun registerUser(email: String, passwordHash: String): RegisterResponse = dbQuery {
        val uuid = UUID.randomUUID().toString()
        User.new {
            this.email = email
            this.password = passwordHash
            this.uuid = uuid
        }
        return@dbQuery RegisterResponse(uuid)
    }

    override suspend fun getAllUsers(): List<User> = dbQuery {
        User.all().toList()
    }

}