package com.loraxx.electrick.onlineshop.domain.repository

import com.loraxx.electrick.onlineshop.domain.model.Product

interface ProductRepository {
    suspend fun getProducts(): List<Product>

    suspend fun getProductDetails(id: Int): Product
}
