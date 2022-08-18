package com.mlallemant.feature_notification.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class NotificationMessage(
    val en: String,
    val fr: String
)