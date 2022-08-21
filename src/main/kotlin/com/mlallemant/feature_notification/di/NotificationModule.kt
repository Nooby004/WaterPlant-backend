package com.mlallemant.feature_notification.di

import com.mlallemant.feature_notification.data.remote.OneSignalServiceImpl
import org.koin.dsl.module

val notificationModule = module {

    single {
        OneSignalServiceImpl(get())
    }

}