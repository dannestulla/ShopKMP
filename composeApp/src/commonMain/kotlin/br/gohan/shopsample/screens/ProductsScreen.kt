package br.gohan.shopsample.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import br.gohan.shopsample.AppParameters
import br.gohan.shopsample.AppRoutes
import br.gohan.shopsample.components.ProductAction
import br.gohan.shopsample.components.ProductComponent
import br.gohan.shopsample.components.topbar.setTopTitle
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview
import presentation.items.ProductsViewModel
import presentation.model.ProductUI

@Composable
fun ProductsScreen(
    currentSearch: String?,
    appParamaters: AppParameters
) = with(appParamaters) {
    val productsViewModel = remember { ProductsViewModel() }
    val products = productsViewModel.state.collectAsState().value.products

    ProductsScreenStateless(products, currentSearch, appParamaters) { action ->
        when (action) {
            is ProductAction.Navigate -> {
                topBarState.title = setTopTitle(AppRoutes.PRODUCT.name)
                coroutine.launch {
                    appParamaters.dataStoreManager.cacheProduct(action.product)
                }
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

            is ProductAction.AddToCart -> {
                productsViewModel.addToCart(action.product)
                coroutine.launch {
                    snackbar.showSnackbar("Added to cart")
                }
            }
        }
    }
}

@Preview
@Composable
fun ProductsScreenStateless(
    products: List<ProductUI>?,
    currentSearch: String?,
    appParamaters: AppParameters,
    onClick: (ProductAction) -> Unit
) {
    if (products == null) {
        LoadingScreen()
        return
    }
    LazyVerticalGrid(
        modifier = Modifier.padding(appParamaters.paddingValues),
        columns = GridCells.Fixed(2)
    ) {
        val filteredProducts = currentSearch?.let {
            products.filter {
                it.title.contains(currentSearch, ignoreCase = true)
            }
        } ?: products
        items(filteredProducts.size) { index ->
            ProductComponent(filteredProducts[index], false, onClick)
        }
    }
}