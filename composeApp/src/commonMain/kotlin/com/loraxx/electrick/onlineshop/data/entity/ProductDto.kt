package com.loraxx.electrick.onlineshop.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class ProductDto(
    val id: Int,
    val title: String,
    val price: Double,
    val description: String,
    val category: String,
    val image: String,
    val rating: RatingDto,
)

@Serializable
data class RatingDto(
    val rate: Double,
    val count: Int
)
