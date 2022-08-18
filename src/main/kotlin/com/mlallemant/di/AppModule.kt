package com.mlallemant.di

import com.mlallemant.feature_auth.data.repository.AuthRepositoryImpl
import com.mlallemant.feature_auth.domain.repository.AuthRepository
import com.mlallemant.feature_auth.domain.use_case.AuthUseCases
import com.mlallemant.feature_auth.domain.use_case.GetAllUsersUseCase
import com.mlallemant.feature_auth.domain.use_case.GetUserByEmailUseCase
import com.mlallemant.feature_auth.domain.use_case.RegisterUserUseCase
import com.mlallemant.feature_notification.data.remote.OneSignalServiceImpl
import com.mlallemant.feature_plant.data.repository.PlantRepositoryImpl
import com.mlallemant.feature_plant.domain.repository.PlantRepository
import com.mlallemant.feature_plant.domain.use_case.*
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val appModule = module {

    // AUTH FEATURE
    singleOf(::AuthRepositoryImpl) { bind<AuthRepository>() }

    single {
        AuthUseCases(
            registerUserUseCase = RegisterUserUseCase(get()),
            getUserByEmailUseCase = GetUserByEmailUseCase(get()),
            getAllUsers = GetAllUsersUseCase(get())
        )
    }

    // PLANT FEATURE
    singleOf(::PlantRepositoryImpl) { bind<PlantRepository>() }

    single {
        PlantUseCases(
            getPlantListUseCase = GetPlantListUseCase(get()),
            savePlantUseCase = SavePlantUseCase(get()),
            addWateringUseCase = AddWateringUseCase(get()),
            getPlantUseCase = GetPlantUseCase(get()),
            deletePlantUseCase = DeletePlantUseCase(get())
        )
    }

    // HTTP CLIENT
    single {
        HttpClient(CIO) {
            install(ContentNegotiation) {
                json()
            }
        }
    }

    single {
        OneSignalServiceImpl(get())

    }

}
