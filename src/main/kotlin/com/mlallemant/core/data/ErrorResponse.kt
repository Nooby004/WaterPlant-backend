package com.mlallemant.core.data

import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
    val errorMessage: String
)
