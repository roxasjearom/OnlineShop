package com.loraxx.electrick.onlineshop.di

import org.koin.core.context.startKoin

fun initKoin() {
    startKoin {
        modules(appModules())
    }
}
