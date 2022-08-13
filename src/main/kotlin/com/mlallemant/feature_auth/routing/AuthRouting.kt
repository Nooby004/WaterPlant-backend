package com.mlallemant.feature_auth.routing

import com.mlallemant.core.data.ErrorResponse
import com.mlallemant.core.data.SuccessResponse
import com.mlallemant.core.jwt.JWT
import com.mlallemant.feature_auth.domain.use_case.AuthUseCases
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import org.koin.ktor.ext.inject
import org.mindrot.jbcrypt.BCrypt

@Serializable
data class LoginRegister(val email: String, val password: String)

fun Application.configureAuthRouting() {

    val authUseCases: AuthUseCases by inject()

    routing {
        post("/signup") {
            val creds = call.receive<LoginRegister>()
            try {
                val response =
                    authUseCases.registerUserUseCase(creds.email, BCrypt.hashpw(creds.password, BCrypt.gensalt()))
                call.respond(SuccessResponse(response))
            } catch (e: Exception) {
                call.respond(HttpStatusCode.Conflict, ErrorResponse("Account already exist"))
            }
        }
        post("/signin") {
            val creds = call.receive<LoginRegister>()
            val user = authUseCases.getUserByEmailUseCase(creds.email)
            if (user == null || !BCrypt.checkpw(creds.password, user.password)) {
                call.respond(HttpStatusCode.NotFound, ErrorResponse("User not found"))
            } else {
                val token = JWT.createJwtToken(user.email)
                call.respond(
                    SuccessResponse(
                        hashMapOf(
                            "token" to token,
                            "email" to user.email,
                            "id" to user.uuid
                        )
                    )
                )
            }
        }
    }
}