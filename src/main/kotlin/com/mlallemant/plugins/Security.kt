package com.mlallemant.plugins


import com.mlallemant.core.jwt.JWT
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*

fun Application.configureSecurity() {

    authentication {
        jwt {
            realm = JWT.jwtRealm    //not sure its purpose, from generated code

            verifier(
                JWT.jwtVerifier
            )
            validate { credential ->
                UserIdPrincipal(credential.payload.getClaim("email").asString())
            }
        }
    }
}
