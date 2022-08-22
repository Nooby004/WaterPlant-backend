package com.mlallemant.plugins

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


fun Application.configureDefaultRouting() {

    routing {

        get("/") {
            call.respond("ping successful")
        }

    }
}