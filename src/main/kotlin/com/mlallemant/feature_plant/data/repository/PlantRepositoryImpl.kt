package com.mlallemant.feature_plant.data.repository

import com.mlallemant.core.data.Transaction.dbQuery
import com.mlallemant.core.extension.sortWateringDescending
import com.mlallemant.feature_auth.domain.model.User
import com.mlallemant.feature_plant.domain.model.Plant
import com.mlallemant.feature_plant.domain.model.PlantData
import com.mlallemant.feature_plant.domain.model.PlantTable
import com.mlallemant.feature_plant.domain.model.WaterPlant
import com.mlallemant.feature_plant.domain.repository.PlantRepository
import com.mlallemant.feature_plant.routing.AddWaterPlantRequest
import com.mlallemant.feature_plant.routing.SavePlantRequest

class PlantRepositoryImpl : PlantRepository {

    override suspend fun getPlantList(user: User): List<PlantData> = dbQuery {
        Plant.find {
            (PlantTable.user eq user.id)
        }.toList().map { it.toData().sortWateringDescending() }

    }

    override suspend fun getPlant(user: User, uuid: String): PlantData? = dbQuery {
        Plant.find {
            (PlantTable.user eq user.id)
            (PlantTable.uuid eq uuid)
        }.firstOrNull()?.toData()?.sortWateringDescending()
    }

    override suspend fun deletePlant(user: User, uuid: String) = dbQuery {
        val plant = Plant.find {
            (PlantTable.user eq user.id)
            (PlantTable.uuid eq uuid)
        }.firstOrNull() ?: throw Exception("Can't delete an unknown plant")

        plant.waterPlants.forEach {
            it.delete()
        }

        plant.delete()
    }

    override suspend fun savePlant(savePlantRequest: SavePlantRequest, user: User): Unit = dbQuery {
        val plant = Plant.find {
            (PlantTable.user eq user.id)
            (PlantTable.uuid eq savePlantRequest.uuid)
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
                it.name = savePlantRequest.name
                it.pictureUrl = savePlantRequest.pictureUrl
                it.waterFrequency = savePlantRequest.waterFrequency
                it.flush()
            }
        }
    }

    override suspend fun addWatering(user: User, addWaterPlantRequest: AddWaterPlantRequest): Unit = dbQuery {
        val plant = Plant.find {
            (PlantTable.user eq user.id)
            (PlantTable.uuid eq addWaterPlantRequest.uuid)
        }.firstOrNull() ?: throw Exception("Can't watering an unknown plant")


        WaterPlant.new {
            this.plant = plant
            this.pictureUrl = addWaterPlantRequest.pictureUrl
            this.creationDate = addWaterPlantRequest.creationDate
        }
    }
}