package com.mlallemant.feature_plant.data.repository

import com.mlallemant.core.data.Transaction.dbQuery
import com.mlallemant.feature_auth.domain.model.User
import com.mlallemant.feature_plant.domain.model.Plant
import com.mlallemant.feature_plant.domain.model.PlantData
import com.mlallemant.feature_plant.domain.model.PlantTable
import com.mlallemant.feature_plant.domain.model.WaterPlant
import com.mlallemant.feature_plant.domain.repository.PlantRepository
import com.mlallemant.feature_plant.routing.PlantParameter
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

    override suspend fun upsertPlant(plantParameter: PlantParameter, uuid: String, user: User): Unit = dbQuery {
        if (plantParameter.uuid == null) {
            // creation
            Plant.new {
                this.user = user
                this.name = plantParameter.name
                this.uuid = uuid
                this.waterFrequency = plantParameter.waterFrequency
                this.pictureUrl = plantParameter.pictureUrl
            }
        } else {
            // update
            val plant = Plant.find {
                (PlantTable.user eq user.id)
                (PlantTable.uuid eq uuid)
            }.firstOrNull()

            plant?.let {
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