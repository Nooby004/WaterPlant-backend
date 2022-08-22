package com.mlallemant

import com.mlallemant.di.appModule
import com.mlallemant.feature_auth.domain.di.authModule
import com.mlallemant.feature_auth.routing.configureAuthRouting
import com.mlallemant.feature_identification.domain.di.identificationModule
import com.mlallemant.feature_identification.routing.configureIdentificationRouting
import com.mlallemant.feature_notification.di.notificationModule
import com.mlallemant.feature_plant.domain.di.plantModule
import com.mlallemant.feature_plant.routing.configurePlantRouting
import com.mlallemant.plugins.configureDefaultRouting
import com.mlallemant.plugins.configureMonitoring
import com.mlallemant.plugins.configureSecurity
import com.mlallemant.plugins.configureSerialization
import io.ktor.server.application.*
import org.koin.ktor.plugin.Koin

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused")
// application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {
    // Install Koin
    install(Koin) {
        modules(appModule, authModule, plantModule, notificationModule, identificationModule)

    }
    configureSerialization()
    configureMonitoring()
    configureSecurity()
    DatabaseFactory.init()
    configureDefaultRouting()
    configureAuthRouting()
    configurePlantRouting()
    configureIdentificationRouting()
}
