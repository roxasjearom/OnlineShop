package com.loraxx.electrick.onlineshop.data.mapper

import com.loraxx.electrick.onlineshop.data.entity.ProductDto
import com.loraxx.electrick.onlineshop.data.entity.RatingDto
import com.loraxx.electrick.onlineshop.domain.model.Product
import com.loraxx.electrick.onlineshop.domain.model.Rating

fun ProductDto.toProduct(): Product {
    return Product(
        id = id,
        title = title,
        price = price,
        description = description,
        category = category,
        image = image,
        rating = rating.toRating()
    )
}

fun RatingDto.toRating(): Rating {
    return Rating(
        rate = rate,
        count = count
    )
}
