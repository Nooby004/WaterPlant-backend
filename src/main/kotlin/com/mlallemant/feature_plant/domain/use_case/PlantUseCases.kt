package com.mlallemant.feature_plant.domain.use_case

data class PlantUseCases(
    val getPlantListUseCase: GetPlantListUseCase,
    val savePlantUseCase: SavePlantUseCase,
    val addWateringUseCase: AddWateringUseCase,
    val getPlantUseCase: GetPlantUseCase,
    val deletePlantUseCase: DeletePlantUseCase,
    val updateWateringNotifyDateUseCase: UpdateWateringNotifyDateUseCase
)
