package com.mlallemant.feature_identification.domain.use_case

import com.mlallemant.feature_identification.domain.model.IdentificationData
import com.mlallemant.feature_identification.domain.repository.IdentificationRepository
import com.mlallemant.feature_identification.routing.IdentifyPlantRequest

class IdentifyPlantUseCase(
    private val repository: IdentificationRepository
) {

    suspend operator fun invoke(identifyPlantRequest: IdentifyPlantRequest): List<IdentificationData>? {
        return repository.identifyPlant(identifyPlantRequest)
    }
}