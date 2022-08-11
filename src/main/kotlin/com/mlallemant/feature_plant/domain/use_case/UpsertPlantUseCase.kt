package com.mlallemant.feature_plant.domain.use_case

import com.mlallemant.feature_auth.domain.model.User
import com.mlallemant.feature_plant.domain.repository.PlantRepository
import com.mlallemant.feature_plant.routing.PlantParameter
import java.util.*

class UpsertPlantUseCase(
    private val repository: PlantRepository
) {
    suspend operator fun invoke(param: PlantParameter, user: User) {
        val uuid = param.uuid ?: UUID.randomUUID().toString()
        return repository.upsertPlant(param, uuid, user)
    }
}