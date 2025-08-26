package com.loraxx.electrick.onlineshop

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import coil3.ImageLoader
import coil3.compose.setSingletonImageLoaderFactory
import coil3.request.crossfade
import com.loraxx.electrick.onlineshop.navigation.Route
import com.loraxx.electrick.onlineshop.ui.productdetails.ProductDetailsScreen
import com.loraxx.electrick.onlineshop.ui.productdetails.ProductDetailsViewModel
import com.loraxx.electrick.onlineshop.ui.productlist.ProductListScreen
import com.loraxx.electrick.onlineshop.ui.theme.OnlineShopTheme
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
@Preview
fun App() {
    setSingletonImageLoaderFactory { context ->
        ImageLoader.Builder(context)
            .crossfade(true)
            .build()
    }
    OnlineShopTheme {
        OnlineShopApp()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnlineShopApp() {
    val navController = rememberNavController()
    val scrollBehavior =
        TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    NavHost(
        navController = navController,
        startDestination = Route.ProductList,
    ) {
        composable<Route.ProductList> {
            ProductListScreen(
                scrollBehavior = scrollBehavior,
                onProductClick = { id ->
                    navController.navigate(Route.ProductDetails(id))
                }
            )
        }
        composable<Route.ProductDetails> { backStackEntry ->
            val productId = backStackEntry.toRoute<Route.ProductDetails>().id
            val productDetailsViewModel: ProductDetailsViewModel = koinViewModel {
                parametersOf(productId)
            }
            ProductDetailsScreen(viewModel = productDetailsViewModel, navController = navController)
        }
    }
}
