package com.mlallemant.di

import com.mlallemant.feature_auth.data.repository.AuthRepositoryImpl
import com.mlallemant.feature_auth.domain.repository.AuthRepository
import com.mlallemant.feature_auth.domain.use_case.AuthUseCases
import com.mlallemant.feature_auth.domain.use_case.GetUserByEmailUseCase
import com.mlallemant.feature_auth.domain.use_case.RegisterUserUseCase
import com.mlallemant.feature_plant.data.repository.PlantRepositoryImpl
import com.mlallemant.feature_plant.domain.repository.PlantRepository
import com.mlallemant.feature_plant.domain.use_case.AddWateringUseCase
import com.mlallemant.feature_plant.domain.use_case.GetPlantListUseCase
import com.mlallemant.feature_plant.domain.use_case.PlantUseCases
import com.mlallemant.feature_plant.domain.use_case.UpsertPlantUseCase
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val appModule = module {

    // AUTH FEATURE
    singleOf(::AuthRepositoryImpl) { bind<AuthRepository>() }

    single {
        AuthUseCases(
            registerUserUseCase = RegisterUserUseCase(get()),
            getUserByEmailUseCase = GetUserByEmailUseCase(get())
        )
    }

    // PLANT FEATURE
    singleOf(::PlantRepositoryImpl) { bind<PlantRepository>() }

    single {
        PlantUseCases(
            getPlantListUseCase = GetPlantListUseCase(get()),
            upsertPlantUseCase = UpsertPlantUseCase(get()),
            addWateringUseCase = AddWateringUseCase(get())
        )
    }
}
