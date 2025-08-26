package com.loraxx.electrick.onlineshop.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Route {
    @Serializable
    data object ProductList : Route()

    @Serializable
    data class ProductDetails(val id: Int): Route()
}
