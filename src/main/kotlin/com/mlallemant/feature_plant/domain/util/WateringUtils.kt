package com.mlallemant.feature_plant.domain.util

import com.mlallemant.feature_plant.domain.model.PlantData
import java.util.*
import java.util.concurrent.TimeUnit

class WateringUtils {

    companion object {

        fun getNextWateringDay(plantModel: PlantData): Long {
            plantModel.let {
                val frequencyWatering = it.waterFrequency
                val waterPlants = it.waterPlants

                return if (waterPlants.isEmpty()) {
                    // if no water plant, you should water your plant now !
                    0
                } else {

                    // Get last watering timestamp
                    val lastWaterPlantTimestamp =
                        waterPlants.maxByOrNull { it1 -> it1.creationDate }?.creationDate?.toLong()

                    // Calculate the future watering day timestamp
                    val nextWateringDayTimestamp =
                        lastWaterPlantTimestamp?.plus((1000 * 60 * 60 * 24 * frequencyWatering.toLong()))

                    // Calculate the number of day before the next watering
                    val numberOfDayBeforeNextWatering =
                        nextWateringDayTimestamp?.minus(Calendar.getInstance().timeInMillis) ?: 0
                    TimeUnit.MILLISECONDS.toDays(
                        if (numberOfDayBeforeNextWatering <= 0) 0 else numberOfDayBeforeNextWatering
                    )
                }
            }
        }

        fun getNumberOfDaySinceLastWateringNotify(plantData: PlantData): Long {
            plantData.let {
                return if (plantData.wateringNotifyDate.isEmpty()) {
                    -1
                } else {
                    TimeUnit.MILLISECONDS.toDays(
                        Calendar.getInstance().timeInMillis.minus(plantData.wateringNotifyDate.toLong())
                    )
                }
            }
        }

    }
}