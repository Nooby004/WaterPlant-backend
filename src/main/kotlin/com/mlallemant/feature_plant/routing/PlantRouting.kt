package com.mlallemant.feature_plant.routing

import com.mlallemant.core.extension.userEmail
import com.mlallemant.feature_auth.domain.use_case.AuthUseCases
import com.mlallemant.feature_plant.domain.use_case.PlantUseCases
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import org.koin.ktor.ext.inject

@Serializable
data class PlantParameter(val uuid: String? = null, val name: String, val waterFrequency: Int, val pictureUrl: String)

@Serializable
data class WaterPlantParameter(val uuid: String, val creationDate: String, val pictureUrl: String)

fun Application.configurePlantRouting() {

    val plantUseCases: PlantUseCases by inject()
    val authUseCases: AuthUseCases by inject()

    routing {
        authenticate {

            get("/plants") {
                // retrieve user
                val user = authUseCases.getUserByEmailUseCase(call.userEmail())
                if (user == null) {
                    call.respond(HttpStatusCode.Unauthorized)
                    return@get
                }

                val plants = plantUseCases.getPlantListUseCase(user)
                call.respond(plants)
            }

            post("/plant") {
                // retrieve user
                val user = authUseCases.getUserByEmailUseCase(call.userEmail())
                if (user == null) {
                    call.respond(HttpStatusCode.Unauthorized)
                    return@post
                }

                val param = call.receive<PlantParameter>()
                plantUseCases.upsertPlantUseCase(param, user)

                call.respond("Success!")
            }

            post("/water-plant") {
                // retrieve user
                val user = authUseCases.getUserByEmailUseCase(call.userEmail())
                if (user == null) {
                    call.respond(HttpStatusCode.Unauthorized)
                    return@post
                }

                val param = call.receive<WaterPlantParameter>()
                plantUseCases.addWateringUseCase(param, user)

                call.respond("Success!")
            }

        }


    }
}