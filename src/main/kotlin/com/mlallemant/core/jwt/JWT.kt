package com.mlallemant.core.jwt

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.typesafe.config.ConfigFactory
import io.ktor.server.config.*
import java.util.*

object JWT {
    private val appConfig = HoconApplicationConfig(ConfigFactory.load())

    private val jwtSecret = appConfig.property("jwt.secret").getString()
    private val jwtIssuer = appConfig.property("jwt.issuer").getString()
    private val jwtAudience = appConfig.property("jwt.audience").getString()
    val jwtRealm = appConfig.property("jwt.realm").getString()
    
    fun createJwtToken(email: String): String? {
        return JWT.create()
            .withAudience(jwtAudience)
            .withIssuer(jwtIssuer)
            .withClaim("email", email)
            .sign(Algorithm.HMAC256(jwtSecret))
    }

    val jwtVerifier: JWTVerifier = JWT
        .require(Algorithm.HMAC256(jwtSecret))
        .withAudience(jwtAudience)
        .withIssuer(jwtIssuer)
        .build()
}