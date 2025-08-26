package com.loraxx.electrick.onlineshop.ui.productlist

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListAppBar(
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior?,
) {
    TopAppBar(
        title = { Text("Online Shop") },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        modifier = modifier,
        scrollBehavior = scrollBehavior,
    )
}
