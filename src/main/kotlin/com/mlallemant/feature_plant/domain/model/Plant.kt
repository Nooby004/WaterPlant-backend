package com.mlallemant.feature_plant.domain.model

import com.mlallemant.feature_auth.domain.model.User
import com.mlallemant.feature_auth.domain.model.UserTable
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object PlantTable : IntIdTable(name = "plant") {
    val user = reference("user_id", UserTable)
    val uuid = varchar("uuid", 100).uniqueIndex()
    val name = varchar("name", 100)
    val waterFrequency = integer("water_frequency")
    val pictureUrl = varchar("picture_url", 5000)
    val wateringNotifyDate = varchar("watering_notify_date", 100)
}

class Plant(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Plant>(PlantTable)

    var user by User referencedOn PlantTable.user
    var uuid by PlantTable.uuid
    var name by PlantTable.name
    var waterFrequency by PlantTable.waterFrequency
    var pictureUrl by PlantTable.pictureUrl
    var wateringNotifyDate by PlantTable.wateringNotifyDate
    val waterPlants by WaterPlant referrersOn WaterPlantTable.plant

    fun toData(): PlantData {
        return PlantData(
            uuid,
            name,
            waterFrequency,
            pictureUrl,
            wateringNotifyDate,
            waterPlants.toList().map { it.toData() })
    }
}

@Serializable
data class PlantData(
    val uuid: String,
    val name: String,
    val waterFrequency: Int,
    val pictureUrl: String,
    val wateringNotifyDate: String,
    val waterPlants: List<WaterPlantData>
)