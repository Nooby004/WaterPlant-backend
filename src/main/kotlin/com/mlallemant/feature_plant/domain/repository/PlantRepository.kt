package com.mlallemant.feature_plant.domain.repository

import com.mlallemant.feature_auth.domain.model.User
import com.mlallemant.feature_plant.domain.model.PlantData
import com.mlallemant.feature_plant.routing.AddWaterPlantRequest
import com.mlallemant.feature_plant.routing.SavePlantRequest

interface PlantRepository {

    suspend fun getPlantList(user: User): List<PlantData>

    suspend fun getPlant(user: User, uuid: String): PlantData?

    suspend fun deletePlant(user: User, uuid: String)

    suspend fun savePlant(savePlantRequest: SavePlantRequest, user: User)

    suspend fun updateWateringNotifyDate(user: User, uuid: String, wateringNotifyDate: String)

    suspend fun addWatering(user: User, addWaterPlantRequest: AddWaterPlantRequest)
}