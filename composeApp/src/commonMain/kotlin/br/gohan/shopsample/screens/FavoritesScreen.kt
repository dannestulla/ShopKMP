package br.gohan.shopsample.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import br.gohan.shopsample.AppRoutes
import br.gohan.shopsample.AppParameters
import br.gohan.shopsample.components.ProductAction
import br.gohan.shopsample.components.ProductComponent
import br.gohan.shopsample.ui.Dimens
import kotlinx.coroutines.launch
import presentation.model.ProductUI

@Composable
fun FavoritesScreen(
    product: List<ProductUI>?,
    appParameters: AppParameters,
) = with(appParameters) {
    FavoritesScreenStateless(product, paddingValues) { action ->
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
    product: List<ProductUI>? = emptyList(),
    paddingValues: PaddingValues,
    action: (ProductAction) -> Unit
) {
    if (product == null) {
        LoadingScreen()
        return
    }

    Column(
        Modifier.padding(paddingValues),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (product.isEmpty()) {
            Text("No favorites yet")
        } else {
            LazyVerticalGrid(
                modifier = Modifier.padding(Dimens.paddingMedium),
                columns = GridCells.Fixed(2)
            ) {
                items(product.size) { index ->
                    ProductComponent(product[index], true, action)
                }
            }
        }
    }
}
