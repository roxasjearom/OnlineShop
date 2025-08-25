package com.loraxx.electrick.onlineshop

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import coil3.ImageLoader
import coil3.compose.setSingletonImageLoaderFactory
import coil3.request.crossfade
import coil3.util.DebugLogger
import com.loraxx.electrick.onlineshop.ui.productlist.ProductListScreen
import com.loraxx.electrick.onlineshop.ui.theme.OnlineShopTheme
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
@Preview
fun App() {
    setSingletonImageLoaderFactory { context ->
        ImageLoader.Builder(context)
            .crossfade(true)
            .build()
    }

    OnlineShopTheme {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
                .safeContentPadding()
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            ProductListScreen { productId ->
                //TODO navigate to product details screen
            }
        }
    }
}
