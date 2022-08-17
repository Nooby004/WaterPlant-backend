package com.mlallemant.feature_plant.routing

import com.mlallemant.core.data.ErrorResponse
import com.mlallemant.core.data.SuccessResponse
import com.mlallemant.core.extension.userEmail
import com.mlallemant.feature_auth.domain.use_case.AuthUseCases
import com.mlallemant.feature_plant.domain.model.WaterPlantTable.plant
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
data class SavePlantRequest(val uuid: String, val name: String, val waterFrequency: Int, val pictureUrl: String)

@Serializable
data class AddWaterPlantRequest(val uuid: String, val creationDate: String, val pictureUrl: String)

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
                call.respond(SuccessResponse(plants))
            }

            get("/plant/{id}") {
                // retrieve user
                val user = authUseCases.getUserByEmailUseCase(call.userEmail())
                if (user == null) {
                    call.respond(HttpStatusCode.Unauthorized)
                    return@get
                }

                val plantId = call.parameters["id"]
                if (plantId == null) {
                    call.respond(HttpStatusCode.BadRequest, ErrorResponse("Bad parameter"))
                } else {
                    val plant = plantUseCases.getPlantUseCase(user, plantId)
                    if (plant != null) {
                        call.respond(SuccessResponse(plant))
                    } else {
                        call.respond(HttpStatusCode.NotFound, ErrorResponse("Plant not found"))
                    }
                }
            }

            delete("plant/{id}") {
                // retrieve user
                val user = authUseCases.getUserByEmailUseCase(call.userEmail())
                if (user == null) {
                    call.respond(HttpStatusCode.Unauthorized)
                    return@delete
                }

                val plantId = call.parameters["id"]
                if (plantId == null) {
                    call.respond(HttpStatusCode.BadRequest, ErrorResponse("Bad parameter"))
                } else {
                    plantUseCases.deletePlantUseCase(user, plantId)
                    call.respond(SuccessResponse(Unit))
                }
            }

            post("/plant") {
                // retrieve user
                val user = authUseCases.getUserByEmailUseCase(call.userEmail())
                if (user == null) {
                    call.respond(HttpStatusCode.Unauthorized, ErrorResponse("User is not authorized"))
                    return@post
                }

                val request = call.receive<SavePlantRequest>()
                val response = plantUseCases.savePlantUseCase(request, user)

                call.respond(SuccessResponse(response))
            }

            post("/water-plant") {
                // retrieve user
                val user = authUseCases.getUserByEmailUseCase(call.userEmail())
                if (user == null) {
                    call.respond(HttpStatusCode.Unauthorized)
                    return@post
                }

                val param = call.receive<AddWaterPlantRequest>()
               val response = plantUseCases.addWateringUseCase(param, user)

                call.respond(SuccessResponse(response))
            }

        }


    }
}