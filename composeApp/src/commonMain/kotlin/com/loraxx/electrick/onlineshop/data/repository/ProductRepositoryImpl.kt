package com.loraxx.electrick.onlineshop.data.repository

import com.loraxx.electrick.onlineshop.data.api.FakeStoreApi
import com.loraxx.electrick.onlineshop.data.mapper.toProduct
import com.loraxx.electrick.onlineshop.domain.model.Product
import com.loraxx.electrick.onlineshop.domain.repository.ProductRepository

class ProductRepositoryImpl(val fakeStoreApi: FakeStoreApi) : ProductRepository {

    override suspend fun getProducts(): List<Product> {
        return try {
            fakeStoreApi.getProducts().map { it.toProduct() }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    override suspend fun getProductDetails(id: Int): Product {
        return fakeStoreApi.getProductDetails(id).toProduct()
    }
}
