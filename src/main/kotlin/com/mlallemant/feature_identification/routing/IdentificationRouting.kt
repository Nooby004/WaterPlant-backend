package com.mlallemant.feature_identification.routing

import com.mlallemant.core.data.ErrorResponse
import com.mlallemant.core.data.SuccessResponse
import com.mlallemant.core.extension.userEmail
import com.mlallemant.feature_auth.domain.use_case.AuthUseCases
import com.mlallemant.feature_identification.domain.use_case.IdentificationUseCases
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import org.koin.ktor.ext.inject


@Serializable
data class IdentifyPlantRequest( // represent a list of pair<image byte array, organs>
    val images: List<Pair<ByteArray, String>>,
    val language: String,
    val includeRelatedPhoto: Boolean
)

fun Application.configureIdentificationRouting() {

    val authUseCases: AuthUseCases by inject()
    val identificationUseCases: IdentificationUseCases by inject()

    routing {
        authenticate {

            post("/identify") {

                // retrieve user
                val user = authUseCases.getUserByEmailUseCase(call.userEmail())
                if (user == null) {
                    call.respond(HttpStatusCode.Unauthorized, ErrorResponse("User is not authorized"))
                    return@post
                }

                val request = call.receive<IdentifyPlantRequest>()
                val response = identificationUseCases.identifyPlantUseCase(request)

                if (response.isNullOrEmpty()) {
                    call.respond(ErrorResponse("Cannot identify the plant, please retry by adding more detailed photo "))
                }

                call.respond(SuccessResponse(response))
            }

        }
    }
}