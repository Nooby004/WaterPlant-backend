package com.mlallemant.feature_plant.domain.repository

import com.mlallemant.feature_auth.domain.model.User
import com.mlallemant.feature_plant.domain.model.Plant
import com.mlallemant.feature_plant.domain.model.PlantData
import com.mlallemant.feature_plant.routing.PlantParameter
import com.mlallemant.feature_plant.routing.WaterPlantParameter

interface PlantRepository {

    suspend fun getPlantList(user: User): List<PlantData>

    suspend fun getPlant(user: User, uuid: String): Plant?

    suspend fun upsertPlant(plantParameter: PlantParameter, uuid: String, user: User)

    suspend fun addWatering(waterPlantParameter: WaterPlantParameter, plant: Plant)
}