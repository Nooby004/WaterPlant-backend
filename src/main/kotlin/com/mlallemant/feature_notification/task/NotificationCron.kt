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
import java.util.*

fun Application.main() {

    val oneSignalService: OneSignalServiceImpl by inject()
    val plantUseCases: PlantUseCases by inject()
    val authUseCases: AuthUseCases by inject()


    val appConfig = HoconApplicationConfig(ConfigFactory.load())
    val appId = appConfig.property("onesignal.app_id").getString()

    launch {

        log.debug("Init cron task")

        while (true) {

            try {
                log.debug("Start cron task")

                // retrieve all users
                val users = authUseCases.getAllUsers()

                if (users.isNotEmpty()) {

                    // loop over it
                    users.forEach { user ->

                        val plants = plantUseCases.getPlantListUseCase(user)
                        if (plants.isNotEmpty()) {
                            plants.forEach { plant ->

                                val numberOfDaySinceLastWatering =
                                    WateringUtils.getNumberOfDaySinceLastWateringNotify(plant)
                                log.debug("User was notified for the plant " + plant.name + " " + numberOfDaySinceLastWatering + " day ago")

                                if (WateringUtils.getNextWateringDay(plant) <= 0L && (numberOfDaySinceLastWatering == -1L || numberOfDaySinceLastWatering >= 1)) {

                                    val message: String = if (numberOfDaySinceLastWatering == -1L) {
                                        plant.name + " va crever si tu l'arroses pas !"
                                    } else {
                                        plant.name + " va vraiment crever si tu l'arroses pas ! Ca fait " + numberOfDaySinceLastWatering + " jour(s) que cette " +
                                                "malheureuse plante a besoin d'eau :("
                                    }

                                    val isSuccess = oneSignalService.sendNotification(
                                        Notification(
                                            includeExternalUserIds = listOf(user.uuid),
                                            headings = NotificationMessage(
                                                en = "Your plant need you",
                                                fr = "Yoo ma caille !"
                                            ),
                                            contents = NotificationMessage(
                                                en = "Please, water " + plant.name,
                                                fr = message
                                            ),
                                            appId = appId
                                        )
                                    )

                                    if (isSuccess) {
                                        log.debug("Notification sent to " + user.email + " successfully")

                                        plantUseCases.updateWateringNotifyDateUseCase(
                                            user,
                                            plant.uuid,
                                            Calendar.getInstance().timeInMillis.toString()
                                        );

                                    } else {
                                        log.debug("Notification sent to " + user.email + " is in error")
                                    }
                                }
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                log.error(e.message)
            }



            log.debug("End cron task")
            // every 30 min
            delay(60 * 1000 * 30)
        }

    }

}