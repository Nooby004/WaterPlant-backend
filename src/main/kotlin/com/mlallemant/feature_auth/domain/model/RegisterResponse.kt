package com.mlallemant.feature_auth.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class RegisterResponse(
    val id: String
)