package com.loraxx.electrick.onlineshop.ui.productdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.loraxx.electrick.onlineshop.domain.repository.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductDetailsViewModel(
    val productId: Int,
    val productRepository: ProductRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProductDetailsUiState())
    val uiState: StateFlow<ProductDetailsUiState> = _uiState.asStateFlow()

    init {
        getProductDetails()
    }

    private fun getProductDetails() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            val product = productRepository.getProductDetails(productId)
            _uiState.value = _uiState.value.copy(isLoading = false, product = product)
        }
    }
}
