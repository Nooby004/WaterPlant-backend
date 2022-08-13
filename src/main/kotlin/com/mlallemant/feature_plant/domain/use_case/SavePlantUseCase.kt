package com.mlallemant.feature_plant.domain.use_case

import com.mlallemant.feature_auth.domain.model.User
import com.mlallemant.feature_plant.domain.repository.PlantRepository
import com.mlallemant.feature_plant.routing.SavePlantRequest

class SavePlantUseCase(
    private val repository: PlantRepository
) {
    suspend operator fun invoke(param: SavePlantRequest, user: User) {
        return repository.upsertPlant(param, user)
    }
}