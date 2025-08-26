package com.loraxx.electrick.onlineshop.di

import com.loraxx.electrick.onlineshop.data.api.FakeStoreApi
import com.loraxx.electrick.onlineshop.data.repository.ProductRepositoryImpl
import com.loraxx.electrick.onlineshop.domain.repository.ProductRepository
import com.loraxx.electrick.onlineshop.ui.productdetails.ProductDetailsViewModel
import com.loraxx.electrick.onlineshop.ui.productlist.ProductListViewModel
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

fun appModules() = listOf(productModule, networkModule)

val productModule = module {
    factory<ProductRepository> { ProductRepositoryImpl(fakeStoreApi = get()) }
    viewModel { ProductListViewModel(productRepository = get()) }

    viewModel { (id: Int) ->
        ProductDetailsViewModel(productId = id, productRepository = get())
    }
}

val networkModule = module {
    single {
        HttpClient {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    useAlternativeNames = false
                })
            }
        }
    }
    single { FakeStoreApi(httpClient = get()) }
}
