package com.mlallemant.feature_plant.domain.use_case

import com.mlallemant.feature_auth.domain.model.User
import com.mlallemant.feature_plant.domain.repository.PlantRepository
import com.mlallemant.feature_plant.routing.AddWaterPlantRequest

class AddWateringUseCase(
    private val repository: PlantRepository
) {
    suspend operator fun invoke(addWaterPlantRequest: AddWaterPlantRequest, user: User) {
        return repository.addWatering(user, addWaterPlantRequest)
    }
}
