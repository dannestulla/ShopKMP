package br.gohan.shopsample.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import br.gohan.shopsample.AppRoutes
import br.gohan.shopsample.ShopParameters
import br.gohan.shopsample.components.ProductAction
import br.gohan.shopsample.components.ProductComponent
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import org.koin.core.parameter.parametersOf
import presentation.ProductsViewModel
import presentation.model.ProductUI

@Composable
fun ProductsScreen(
    currentSearch: String?,
    shopParameters: ShopParameters,
    category: String,
    productsViewModel: ProductsViewModel = koinInject { parametersOf(category) }
) = with(shopParameters) {
    val products = productsViewModel.state.collectAsState().value.products

    ProductsScreenStateless(products, currentSearch, shopParameters) { action ->
        when (action) {
            is ProductAction.Navigate -> {
                coroutine.launch {
                    dataStoreManager.cacheProduct(action.product)
                }
                navController.navigate(AppRoutes.PRODUCT.name)
            }

            is ProductAction.Favorite -> {
                productsViewModel.saveFavorite(action.product)
                coroutine.launch {
                    snackbar.showSnackbar("Added to favorites")
                }
            }

            is ProductAction.RemoveFavorite -> {
                productsViewModel.removeFavorite(action.product)
                coroutine.launch {
                    snackbar.showSnackbar("Removed from favorites")
                }
            }

            is ProductAction.AddToCart -> {
                productsViewModel.addToCheckout(action.product)
                coroutine.launch {
                    snackbar.showSnackbar("Added to cart")
                }
            }
        }
    }
}

@Composable
fun ProductsScreenStateless(
    products: List<ProductUI>?,
    currentSearch: String?,
    appParameters: ShopParameters,
    onClick: (ProductAction) -> Unit
) {
    if (products == null) {
        LoadingScreen()
        return
    }
    LazyVerticalGrid(
        modifier = Modifier.padding(appParameters.paddingValues),
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