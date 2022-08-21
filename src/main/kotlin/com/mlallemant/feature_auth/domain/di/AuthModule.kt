package com.mlallemant.feature_auth.domain.di

import com.mlallemant.feature_auth.data.repository.AuthRepositoryImpl
import com.mlallemant.feature_auth.domain.repository.AuthRepository
import com.mlallemant.feature_auth.domain.use_case.AuthUseCases
import com.mlallemant.feature_auth.domain.use_case.GetAllUsersUseCase
import com.mlallemant.feature_auth.domain.use_case.GetUserByEmailUseCase
import com.mlallemant.feature_auth.domain.use_case.RegisterUserUseCase
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module


val authModule = module {

    singleOf(::AuthRepositoryImpl) { bind<AuthRepository>() }

    single {
        AuthUseCases(
            registerUserUseCase = RegisterUserUseCase(get()),
            getUserByEmailUseCase = GetUserByEmailUseCase(get()),
            getAllUsers = GetAllUsersUseCase(get())
        )
    }
}