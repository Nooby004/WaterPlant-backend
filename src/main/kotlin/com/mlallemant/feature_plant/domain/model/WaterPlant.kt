package com.mlallemant.feature_plant.domain.model

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object WaterPlantTable : IntIdTable(name = "water_plant") {
    val plant = reference("plant_id", PlantTable)
    val pictureUrl = varchar("picture_url", 5000)
    val creationDate = varchar("creation_date", 100)
}

class WaterPlant(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<WaterPlant>(WaterPlantTable)

    var plant by Plant referencedOn WaterPlantTable.plant
    var pictureUrl by WaterPlantTable.pictureUrl
    var creationDate by WaterPlantTable.creationDate

    fun toData(): WaterPlantData {
        return WaterPlantData(pictureUrl, creationDate)
    }
}

@Serializable
data class WaterPlantData(
    val pictureUrl: String,
    val creationDate: String
)
