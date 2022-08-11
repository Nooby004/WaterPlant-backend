package com.mlallemant.feature_auth.domain.model

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable


object UserTable : IntIdTable(name = "users") {
    val email = varchar(name = "email", length = 100)
    val password = varchar(name = "password", length = 100)
}

class User(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<User>(UserTable)

    var email by UserTable.email
    var password by UserTable.password
}

