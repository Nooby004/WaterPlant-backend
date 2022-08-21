package com.mlallemant.feature_identification.data.remote.api

import com.mlallemant.feature_identification.data.remote.dto.IdentifyResponse
import com.mlallemant.feature_identification.routing.IdentifyPlantRequest

interface PlantNetApi {

    suspend fun identify(identifyPlantRequest: IdentifyPlantRequest): IdentifyResponse?

    companion object {
        const val URL = "https://my-api.plantnet.org/v2/identify/all"
    }
}