package com.mlallemant.feature_identification.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class IdentificationData(
    val scientificName: String = "",
    val commonName: String = "",
    val score: Float,
    val organs: String = "",
    val family: String = "",
    val imageUrl: String = "",
)
