package com.loraxx.electrick.onlineshop

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform