package com.mlallemant.feature_identification.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class Query(
    val project: String,
    val images: List<String>,
    val organs: List<String>,
    val includeRelatedImages: Boolean
)
