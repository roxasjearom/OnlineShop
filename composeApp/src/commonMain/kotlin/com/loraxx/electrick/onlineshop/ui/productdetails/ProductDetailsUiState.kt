package com.loraxx.electrick.onlineshop.ui.productdetails

import com.loraxx.electrick.onlineshop.domain.model.Product

data class ProductDetailsUiState(
    val isLoading: Boolean = false,
    val product: Product? = null,
)
