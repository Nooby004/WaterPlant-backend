package com.mlallemant.core.extension

import io.ktor.server.application.*
import io.ktor.server.auth.*

fun ApplicationCall.userEmail(): String {
    return this.principal<UserIdPrincipal>()?.name!!
}