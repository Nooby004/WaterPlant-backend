package com.mlallemant.core.extension

import com.mlallemant.feature_plant.domain.model.PlantData

fun PlantData.sortWateringDescending(): PlantData {
    return this.copy(
        waterPlants = this.waterPlants.sortedByDescending { it.creationDate }
    )
}