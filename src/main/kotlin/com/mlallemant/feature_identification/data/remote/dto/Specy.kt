package com.mlallemant.feature_identification.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class Specy(
    val scientificNameWithoutAuthor: String,
    val scientificNameAuthorship: String,
    val genus: Genus,
    val family: Family,
    val commonNames: List<String>,
    val scientificName: String,
)

@Serializable
data class Genus(
    val scientificNameWithoutAuthor: String,
    val scientificNameAuthorship: String,
    val scientificName: String,
)

@Serializable
data class Family(
    val scientificNameWithoutAuthor: String,
    val scientificNameAuthorship: String,
    val scientificName: String,
)