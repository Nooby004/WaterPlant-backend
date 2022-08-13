package com.mlallemant.feature_plant.domain.repository

import com.mlallemant.feature_auth.domain.model.User
import com.mlallemant.feature_plant.domain.model.Plant
import com.mlallemant.feature_plant.domain.model.PlantData
import com.mlallemant.feature_plant.routing.SavePlantRequest
import com.mlallemant.feature_plant.routing.WaterPlantParameter

interface PlantRepository {

    suspend fun getPlantList(user: User): List<PlantData>

    suspend fun getPlant(user: User, uuid: String): Plant?

    suspend fun upsertPlant(savePlantRequest: SavePlantRequest, user: User)

    suspend fun addWatering(waterPlantParameter: WaterPlantParameter, plant: Plant)
}