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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.StarHalf
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LoadingIndicator
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
import com.loraxx.electrick.onlineshop.util.formatToUsd
import onlineshop.composeapp.generated.resources.Res
import onlineshop.composeapp.generated.resources.add_to_cart
import onlineshop.composeapp.generated.resources.buy_now
import onlineshop.composeapp.generated.resources.cd_favorite
import onlineshop.composeapp.generated.resources.cd_share_product
import onlineshop.composeapp.generated.resources.header_product_details
import onlineshop.composeapp.generated.resources.product_not_found
import onlineshop.composeapp.generated.resources.rating_count
import onlineshop.composeapp.generated.resources.rating_display
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
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
        },
        bottomBar = {
            ActionButtonsRow(
                modifier = Modifier
                    .fillMaxWidth(),
                onAddToCartClick = { /*trigger add to cart action*/ },
                onBuyNowClick = { /*trigger buy now action*/ },
            )
        }
    ) { innerPadding ->
        if (uiState.isLoading) {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                LoadingIndicator()
            }
        } else {
            if (product != null) {
                ProductDetailsScreen(
                    modifier = modifier
                        .padding(innerPadding),
                    product = product,
                    onShareClick = { /*trigger share action*/ },
                    onRatingsClick = { /*trigger ratings action*/ }
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
    onRatingsClick: () -> Unit
) {
    var isFavorite by remember { mutableStateOf(false) } //internal state for favorite status

    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.verticalScroll(rememberScrollState()).padding(horizontal = 16.dp)
        ) {
            AsyncImage(
                model = product.image,
                contentDescription = null,
                modifier = Modifier.fillMaxWidth().height(352.dp),
                contentScale = ContentScale.Fit,
            )
            Spacer(modifier = Modifier.height(16.dp))
            PriceActionsRow(
                price = product.price.formatToUsd(),
                isFavorite = isFavorite,
                onFavoriteClick = { isFavorite = !isFavorite },
                onShareClick = onShareClick,
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = product.title,
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(modifier = Modifier.height(16.dp))
            ProductDetailsSection(details = product.description)
            Spacer(modifier = Modifier.height(16.dp))
            RatingSection(rating = product.rating, onRatingsClick = onRatingsClick)
        }
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
                imageVector = Icons.Outlined.Share,
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

@OptIn(ExperimentalResourceApi::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun ActionButtonsRow(
    modifier: Modifier = Modifier,
    onAddToCartClick: () -> Unit,
    onBuyNowClick: () -> Unit,
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shadowElevation = 8.dp,
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedButton(
                onClick = onAddToCartClick,
                modifier = Modifier.weight(1f),
                shape = ButtonDefaults.squareShape,
                contentPadding = ButtonDefaults.contentPaddingFor(ButtonDefaults.MediumContainerHeight),
            ) {
                Text(stringResource(Res.string.add_to_cart))
            }

            Button(
                onClick = onBuyNowClick,
                modifier = Modifier.weight(1f),
                shape = ButtonDefaults.squareShape,
                contentPadding = ButtonDefaults.contentPaddingFor(ButtonDefaults.MediumContainerHeight),
            ) {
                Text(stringResource(Res.string.buy_now))
            }
        }
    }
}

@Composable
fun RatingSection(
    modifier: Modifier = Modifier,
    rating: Rating,
    maxStars: Int = 5,
    onRatingsClick: () -> Unit,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = stringResource(Res.string.rating_count, rating.count.toString()),
                modifier = Modifier.padding(vertical = 8.dp).weight(1f),
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
            )

            IconButton(onClick = onRatingsClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = stringResource(Res.string.rating_display, rating.rate.toString()),
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
            )

            for (i in 1..maxStars) {
                val starIcon = when {
                    rating.rate >= i -> Icons.Filled.Star
                    rating.rate >= i - 0.5 -> Icons.AutoMirrored.Filled.StarHalf
                    else -> Icons.Outlined.StarOutline
                }
                Icon(
                    imageVector = starIcon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(24.dp)
                )
            }
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
                        rate = 3.5,
                        count = 100
                    )
                ),
                onShareClick = {},
                onRatingsClick = {},
            )
        }
    }
}
