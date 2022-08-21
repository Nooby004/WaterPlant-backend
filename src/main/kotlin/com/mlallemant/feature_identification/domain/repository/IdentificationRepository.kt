package com.mlallemant.feature_identification.domain.repository

import com.mlallemant.feature_identification.domain.model.IdentificationData
import com.mlallemant.feature_identification.routing.IdentifyPlantRequest

interface IdentificationRepository {

    suspend fun identifyPlant(identifyPlantRequest: IdentifyPlantRequest): List<IdentificationData>?
}