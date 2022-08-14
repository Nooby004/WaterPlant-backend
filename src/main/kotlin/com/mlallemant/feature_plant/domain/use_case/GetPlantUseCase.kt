package com.mlallemant.feature_plant.domain.use_case

import com.mlallemant.feature_auth.domain.model.User
import com.mlallemant.feature_plant.domain.model.PlantData
import com.mlallemant.feature_plant.domain.repository.PlantRepository

class GetPlantUseCase (
    private val repository: PlantRepository
    ) {
    suspend operator fun invoke(user: User, uuid: String): PlantData? {
        return repository.getPlant(user, uuid)
    }
}