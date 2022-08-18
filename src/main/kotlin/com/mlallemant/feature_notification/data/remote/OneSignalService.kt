package com.mlallemant.feature_notification.data.remote

import com.mlallemant.feature_notification.data.remote.dto.Notification

interface OneSignalService {

    suspend fun sendNotification(notification: Notification): Boolean

    companion object {
        const val NOTIFICATIONS = "https://onesignal.com/api/v1/notifications"
    }
}