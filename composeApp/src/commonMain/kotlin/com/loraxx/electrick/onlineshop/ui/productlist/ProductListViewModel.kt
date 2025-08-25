package com.loraxx.electrick.onlineshop.ui.productlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.loraxx.electrick.onlineshop.domain.repository.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProductListViewModel(
    val productRepository: ProductRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProductListUiState())
    val uiState: StateFlow<ProductListUiState> = _uiState.asStateFlow()

    init {
        getProducts()
    }

    private fun getProducts() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val products = productRepository.getProducts()
            _uiState.update {
                it.copy(
                    isLoading = false,
                    products = products,
                )
            }
        }
    }
}
