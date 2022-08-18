package com.mlallemant.feature_notification.task

import com.mlallemant.feature_auth.domain.use_case.AuthUseCases
import com.mlallemant.feature_notification.data.remote.OneSignalServiceImpl
import com.mlallemant.feature_notification.data.remote.dto.Notification
import com.mlallemant.feature_notification.data.remote.dto.NotificationMessage
import com.mlallemant.feature_plant.domain.use_case.PlantUseCases
import com.mlallemant.feature_plant.domain.util.WateringUtils
import com.typesafe.config.ConfigFactory
import io.ktor.server.application.*
import io.ktor.server.config.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.ktor.ext.inject

fun Application.main() {

    val oneSignalService: OneSignalServiceImpl by inject()
    val plantUseCases: PlantUseCases by inject()
    val authUseCases: AuthUseCases by inject()


    val appConfig = HoconApplicationConfig(ConfigFactory.load())
    val appId = appConfig.property("onesignal.app_id").getString()

    launch {

        log.debug("Init cron task")

        while (true) {

            log.debug("Start cron task")

            val users = authUseCases.getAllUsers()

            if (users.isNotEmpty()) {
                users.forEach { user ->

                    val plantNameList: ArrayList<String> = arrayListOf()

                    val plants = plantUseCases.getPlantListUseCase(user)
                    if (plants.isNotEmpty()) {
                        plants.forEach { plant ->
                            if (WateringUtils.getNextWateringDay(plant) <= 0L) {
                                plantNameList.add(plant.name)
                            }
                        }

                        if (plantNameList.isNotEmpty()) {
                            val isSuccess = oneSignalService.sendNotification(
                                Notification(
                                    includeExternalUserIds = listOf(user.uuid),
                                    headings = NotificationMessage(
                                        en = "Your plants need you",
                                        fr = "Vos plantes ont besoin de vous"
                                    ),
                                    contents = NotificationMessage(
                                        en = plantNameList.joinToString(", "),
                                        fr = plantNameList.joinToString(", ")
                                    ),
                                    appId = appId
                                )
                            )
                            log.debug("Notification sent to " + user.email + if (isSuccess) " successfully" else "in error")
                        }
                    }
                }
            }

            log.debug("End cron task")
            // every 25 min
            delay(1500000)


        }
    }
}