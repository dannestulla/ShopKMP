package br.gohan.shopsample.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import br.gohan.shopsample.AppRoutes
import br.gohan.shopsample.ShopParameters
import br.gohan.shopsample.components.ProductAction
import br.gohan.shopsample.components.ProductComponent
import br.gohan.shopsample.ui.Dimens
import kotlinx.coroutines.launch
import presentation.favorites.FavoritesViewModel
import presentation.products.ProductUI

@Composable
fun FavoritesScreen(
    shopParameters: ShopParameters,
) = with(shopParameters) {
    val favoritesViewModel = remember { FavoritesViewModel() }
    val state by favoritesViewModel.state.collectAsState()

    FavoritesScreenStateless(state.products, paddingValues) { action ->
        when (action) {
            is ProductAction.Navigate -> {
                navController.navigate(AppRoutes.PRODUCT.name)
            }
            is ProductAction.Favorite -> {
                favoritesViewModel.saveFavorite(action.product)
                coroutine.launch {
                    snackbar.showSnackbar("Added to favorites")
                }
            }
            is ProductAction.RemoveFavorite -> {
                favoritesViewModel.removeFavorite(action.product)
                coroutine.launch {
                    snackbar.showSnackbar("Removed from favorites")
                }
            }
            is ProductAction.AddToCart -> {}
        }
    }

}

@Composable
fun FavoritesScreenStateless(
    product: List<ProductUI>?,
    paddingValues: PaddingValues,
    action: (ProductAction) -> Unit
) {
    if (product == null) {
        LoadingScreen()
        return
    }

    Column(
        verticalArrangement = if (product.isEmpty()) Arrangement.Center else Arrangement.Top,
        horizontalAlignment = if (product.isEmpty()) Alignment.CenterHorizontally else Alignment.Start,
        modifier = Modifier.padding(paddingValues).fillMaxSize(),
    ) {
        if (product.isEmpty()) {
            Text(
                "No favorites yet", fontSize = Dimens.fontSmall,
                style = TextStyle(
                    fontFamily = MaterialTheme.typography.headlineMedium.fontFamily,
                ),
            )
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2)
            ) {
                items(product.size) { index ->
                    ProductComponent(product[index], true, action)
                }
            }
        }
    }
}
