package com.loraxx.electrick.onlineshop.ui.productdetails

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailsAppBar(
    modifier: Modifier = Modifier,
    canNavigateBack: Boolean,
    onNavigateUp: () -> Unit,
    onCartClick: () -> Unit,
) {
    TopAppBar(
        title = { },
        modifier = modifier,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface,
            scrolledContainerColor = MaterialTheme.colorScheme.surface,
        ),
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = onNavigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back button"
                    )
                }
            }
        },
        actions = {
            IconButton(onClick = onCartClick) {
                Icon(
                    imageVector = Icons.Outlined.ShoppingCart,
                    contentDescription = "Shopping cart"
                )
            }
        },
    )
}
