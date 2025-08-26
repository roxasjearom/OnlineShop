package com.loraxx.electrick.onlineshop.ui.productdetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.loraxx.electrick.onlineshop.domain.model.Product
import com.loraxx.electrick.onlineshop.domain.model.Rating
import com.loraxx.electrick.onlineshop.ui.theme.OnlineShopTheme
import onlineshop.composeapp.generated.resources.Res
import onlineshop.composeapp.generated.resources.add_to_cart
import onlineshop.composeapp.generated.resources.buy_now
import onlineshop.composeapp.generated.resources.cd_favorite
import onlineshop.composeapp.generated.resources.cd_share_product
import onlineshop.composeapp.generated.resources.header_product_details
import onlineshop.composeapp.generated.resources.product_not_found
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ProductDetailsScreen(
    modifier: Modifier = Modifier,
    viewModel: ProductDetailsViewModel,
    navController: NavController
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val product = uiState.product

    Scaffold(
        containerColor = MaterialTheme.colorScheme.surface,
        topBar = {
            ProductDetailsAppBar(
                canNavigateBack = navController.previousBackStackEntry != null,
                onNavigateUp = { navController.navigateUp() },
                onCartClick = {},
            )
        }
    ) { innerPadding ->
        if (uiState.isLoading) {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator()
            }
        } else {
            if (product != null) {
                ProductDetailsScreen(
                    modifier = modifier
                        .padding(innerPadding)
                        .padding(horizontal = 16.dp),
                    product = product,
                    onShareClick = { /*trigger share action*/ },
                    onAddToCartClick = { /*trigger add to cart action*/ },
                    onBuyNowClick = { /*trigger buy now action*/ },
                )
            } else {
                ProductNotFound()
            }
        }
    }
}

@Composable
fun ProductNotFound() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(text = stringResource(Res.string.product_not_found))
    }
}

@Composable
fun ProductDetailsScreen(
    modifier: Modifier = Modifier,
    product: Product,
    onShareClick: () -> Unit,
    onAddToCartClick: () -> Unit,
    onBuyNowClick: () -> Unit,
) {
    var isFavorite by remember { mutableStateOf(false) } //internal state for favorite status

    Box(modifier = modifier.fillMaxSize()) {
        Column {
            AsyncImage(
                model = product.image,
                contentDescription = null,
                modifier = Modifier.fillMaxWidth().height(352.dp),
                contentScale = ContentScale.Fit,
            )
            Spacer(modifier = Modifier.height(16.dp))
            PriceActionsRow(
                price = product.price.toString(),
                isFavorite = isFavorite,
                onFavoriteClick = { isFavorite = !isFavorite },
                onShareClick = onShareClick,
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = product.title,
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(modifier = Modifier.height(16.dp))
            ProductDetailsSection(details = product.description)
        }
        ActionButtonsRow(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth(),
            onAddToCartClick = onAddToCartClick,
            onBuyNowClick = onBuyNowClick,
        )
    }
}

@Composable
fun ProductDetailsSection(modifier: Modifier = Modifier, details: String) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = stringResource(Res.string.header_product_details),
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = details,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun PriceActionsRow(
    modifier: Modifier = Modifier,
    price: String,
    isFavorite: Boolean,
    onFavoriteClick: () -> Unit,
    onShareClick: () -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = price,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f)
        )

        IconButton(onClick = onShareClick) {
            Icon(
                imageVector = Icons.Filled.Share,
                contentDescription = stringResource(Res.string.cd_share_product),
            )
        }

        IconButton(onClick = onFavoriteClick) {
            Icon(
                imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                contentDescription = stringResource(Res.string.cd_favorite),
            )
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun ActionButtonsRow(
    modifier: Modifier = Modifier,
    onAddToCartClick: () -> Unit,
    onBuyNowClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        OutlinedButton(
            onClick = onAddToCartClick,
            modifier = Modifier.weight(1f),
        ) {
            Text(stringResource(Res.string.add_to_cart))
        }

        Button(
            onClick = onBuyNowClick,
            modifier = Modifier.weight(1f),
        ) {
            Text(stringResource(Res.string.buy_now))
        }
    }
}

@Preview
@Composable
fun ProductDetailsScreenPreview() {
    OnlineShopTheme {
        Surface {
            ProductDetailsScreen(
                product = Product(
                    id = 1,
                    title = "Product Title",
                    price = 100.0,
                    description = "Product Description",
                    category = "Category",
                    image = "https://fakestoreapi.com/img/81",
                    rating = Rating(
                        rate = 4.5,
                        count = 100
                    )
                ),
                onShareClick = {},
                onAddToCartClick = {},
                onBuyNowClick = {},
            )
        }
    }
}
