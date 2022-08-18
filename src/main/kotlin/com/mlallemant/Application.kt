package com.mlallemant

import com.mlallemant.di.appModule
import com.mlallemant.feature_auth.routing.configureAuthRouting
import com.mlallemant.feature_notification.data.remote.OneSignalServiceImpl
import com.mlallemant.feature_notification.data.remote.dto.Notification
import com.mlallemant.feature_notification.data.remote.dto.NotificationMessage
import com.mlallemant.feature_plant.routing.configurePlantRouting
import com.mlallemant.plugins.configureMonitoring
import com.mlallemant.plugins.configureSecurity
import com.mlallemant.plugins.configureSerialization
import com.typesafe.config.ConfigFactory
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.config.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.ktor.plugin.Koin

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused")
// application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {

    // Install Koin
    install(Koin) {
        modules(appModule)
    }

    val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json()
        }
    }

    configureSerialization()
    configureMonitoring()
    configureSecurity()
    DatabaseFactory.init()
    configureAuthRouting()

    configurePlantRouting()
    val service = OneSignalServiceImpl(client)

    val appConfig = HoconApplicationConfig(ConfigFactory.load())
    val appId = appConfig.property("onesignal.app_id").getString()

    CoroutineScope(Dispatchers.IO).launch {
        val cul = service.sendNotification(
            Notification(
                includedSegments = listOf("All"),
                headings = NotificationMessage(en = "title", fr = "titre"),
                contents = NotificationMessage(en = "content", fr = "content"),
                appId = appId
            )
        )

        log.debug("CUL = $cul")
    }


}
