package com.loraxx.electrick.onlineshop.data.api

import com.loraxx.electrick.onlineshop.data.entity.ProductDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

const val BASE_URL = "https://fakestoreapi.com"

class FakeStoreApi(val httpClient: HttpClient) {
    suspend fun getProducts(): List<ProductDto> {
        return httpClient.get("$BASE_URL/products").body()
    }
}
