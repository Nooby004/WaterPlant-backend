package com.mlallemant.feature_identification.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class IdentifyResponse(
    val query: Query? = null,
    val language: String? = null,
    val bestMatch: String? = null,
    val preferedReferential: String? = null,
    val results: List<Result>? = null,
    //val version: String? = null,
    val remainingIdentificationRequests: Int? = null

)
