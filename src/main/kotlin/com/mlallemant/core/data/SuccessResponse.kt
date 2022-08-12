package com.mlallemant.core.data

import kotlinx.serialization.Serializable

@Serializable
data class SuccessResponse<T>(
    val data: T
)
