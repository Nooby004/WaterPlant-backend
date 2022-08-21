package com.mlallemant.feature_identification.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class Result(
    val score: Float? = null,
    val species: Specy? = null,
    val images: List<Image>? = null,
    val gbif: Gbif? = null
)

@Serializable
data class Gbif(
    val id: String
)

@Serializable
data class Image(
    val organ: String? = null,
    val url: Url? = null
)

@Serializable
data class Url(
    val o: String? = null,
    val m: String? = null,
    val s: String? = null,
)