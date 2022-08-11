package com.mlallemant.feature_plant.domain.use_case

data class PlantUseCases(
    val getPlantListUseCase: GetPlantListUseCase,
    val upsertPlantUseCase: UpsertPlantUseCase,
    val addWateringUseCase: AddWateringUseCase
)
