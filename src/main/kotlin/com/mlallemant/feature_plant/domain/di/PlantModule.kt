package com.mlallemant.feature_plant.domain.di

import com.mlallemant.feature_plant.data.repository.PlantRepositoryImpl
import com.mlallemant.feature_plant.domain.repository.PlantRepository
import com.mlallemant.feature_plant.domain.use_case.*
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module


val plantModule = module {

    singleOf(::PlantRepositoryImpl) { bind<PlantRepository>() }

    single {
        PlantUseCases(
            getPlantListUseCase = GetPlantListUseCase(get()),
            savePlantUseCase = SavePlantUseCase(get()),
            addWateringUseCase = AddWateringUseCase(get()),
            getPlantUseCase = GetPlantUseCase(get()),
            deletePlantUseCase = DeletePlantUseCase(get()),
            updateWateringNotifyDateUseCase = UpdateWateringNotifyDateUseCase(get())
        )
    }
}