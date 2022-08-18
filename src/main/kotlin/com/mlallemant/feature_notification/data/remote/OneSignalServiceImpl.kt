package com.mlallemant.feature_notification.data.remote

import com.mlallemant.feature_notification.data.remote.dto.Notification
import com.typesafe.config.ConfigFactory
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.config.*

class OneSignalServiceImpl(
    private val client: HttpClient,
) : OneSignalService {

    private val appConfig = HoconApplicationConfig(ConfigFactory.load())
    private val apiKey = appConfig.property("onesignal.api_key").getString()


    override suspend fun sendNotification(notification: Notification): Boolean {
        return try {
            client.post(
                OneSignalService.NOTIFICATIONS
            ) {
                this.contentType(ContentType.Application.Json)
                this.header("Authorization", "Basic $apiKey")
                this.setBody(notification)
            }
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}