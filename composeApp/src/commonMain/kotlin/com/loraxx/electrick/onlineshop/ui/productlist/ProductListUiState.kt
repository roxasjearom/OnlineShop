package com.loraxx.electrick.onlineshop.ui.productlist

import com.loraxx.electrick.onlineshop.domain.model.Product

data class ProductListUiState(
    val isLoading: Boolean = false,
    val products: List<Product> = emptyList(),
)
