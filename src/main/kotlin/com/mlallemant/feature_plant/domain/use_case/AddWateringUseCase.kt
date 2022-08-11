package com.mlallemant.feature_plant.domain.use_case

import com.mlallemant.feature_auth.domain.model.User
import com.mlallemant.feature_plant.domain.repository.PlantRepository
import com.mlallemant.feature_plant.routing.WaterPlantParameter

class AddWateringUseCase(
    private val repository: PlantRepository
) {
    suspend operator fun invoke(waterPlantParameter: WaterPlantParameter, user: User) {
        val plant = repository.getPlant(user, waterPlantParameter.uuid)

        if (plant?.id == null) {
            throw Exception("Plant does not exist")
        }
        return repository.addWatering(waterPlantParameter, plant)
    }
}
