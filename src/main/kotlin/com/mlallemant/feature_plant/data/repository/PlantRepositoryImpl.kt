package com.mlallemant.feature_plant.data.repository

import com.mlallemant.core.data.Transaction.dbQuery
import com.mlallemant.feature_auth.domain.model.User
import com.mlallemant.feature_plant.domain.model.*
import com.mlallemant.feature_plant.domain.repository.PlantRepository
import com.mlallemant.feature_plant.routing.SavePlantRequest
import com.mlallemant.feature_plant.routing.WaterPlantParameter

class PlantRepositoryImpl : PlantRepository {

    override suspend fun getPlantList(user: User): List<PlantData> = dbQuery {
        Plant.find {
            (PlantTable.user eq user.id)
        }.toList().map { it.toData() }

    }

    override suspend fun getPlant(user: User, uuid: String): Plant? = dbQuery {
        Plant.find {
            (PlantTable.user eq user.id)
            (PlantTable.uuid eq uuid)
        }.firstOrNull()
    }

    override suspend fun upsertPlant(savePlantRequest: SavePlantRequest, user: User): Unit = dbQuery {
        val plant = Plant.find {
            (PlantTable.user eq user.id)
            (PlantTable.uuid eq user.uuid)
        }.firstOrNull()

        if (plant == null) {
            // creation
            Plant.new {
                this.user = user
                this.uuid = savePlantRequest.uuid
                this.name = savePlantRequest.name
                this.waterFrequency = savePlantRequest.waterFrequency
                this.pictureUrl = savePlantRequest.pictureUrl
            }
        } else {
            // update
            plant.let {
                it.name = plant.name
                it.pictureUrl = plant.pictureUrl
                it.waterFrequency = plant.waterFrequency
                it.flush()
            }
        }
    }

    override suspend fun addWatering(waterPlantParameter: WaterPlantParameter, plant: Plant): Unit = dbQuery {
        WaterPlant.new {
            this.plant = plant
            this.pictureUrl = waterPlantParameter.pictureUrl
            this.creationDate = waterPlantParameter.creationDate
        }
    }
}