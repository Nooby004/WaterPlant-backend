package com.mlallemant.feature_identification.domain.di

import com.mlallemant.feature_identification.data.remote.api.PlantNetApi
import com.mlallemant.feature_identification.data.remote.api.PlantNetApiImpl
import com.mlallemant.feature_identification.data.remote.repository.IdentificationRepositoryImpl
import com.mlallemant.feature_identification.domain.repository.IdentificationRepository
import com.mlallemant.feature_identification.domain.use_case.IdentificationUseCases
import com.mlallemant.feature_identification.domain.use_case.IdentifyPlantUseCase
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val identificationModule = module {


    singleOf(::IdentificationRepositoryImpl) { bind<IdentificationRepository>() }

    singleOf(::PlantNetApiImpl) { bind<PlantNetApi>() }

    single {
        IdentificationUseCases(
            identifyPlantUseCase = IdentifyPlantUseCase(get())
        )
    }

    /*  single {
          PlantNetApiImpl(get())
      }*/
}