package com.mlallemant.feature_plant.domain.use_case

import com.mlallemant.feature_auth.domain.model.User
import com.mlallemant.feature_plant.domain.repository.PlantRepository

class UpdateWateringNotifyDateUseCase(
    private val repository: PlantRepository
) {
    suspend operator fun invoke(user: User, uuid: String, wateringNotifyDate: String): Unit =
        repository.updateWateringNotifyDate(user, uuid, wateringNotifyDate)
}