package br.gohan.shopsample.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import br.gohan.shopsample.AppRoutes
import br.gohan.shopsample.ShopParameters
import br.gohan.shopsample.components.ProductAction
import br.gohan.shopsample.components.ProductComponent
import br.gohan.shopsample.components.topbar.setTopTitle
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview
import presentation.products.ProductUI
import presentation.products.ProductsViewModel

@Composable
fun ProductsScreen(
    currentSearch: String?,
    shopParameters: ShopParameters
) = with(shopParameters) {
    val productsViewModel = remember { ProductsViewModel() }
    val products = productsViewModel.state.collectAsState().value.products

    ProductsScreenStateless(products, currentSearch, shopParameters) { action ->
        when (action) {
            is ProductAction.Navigate -> {
                topBarState.title = setTopTitle(AppRoutes.PRODUCT.name)
                coroutine.launch {
                    shopParameters.dataStoreManager.cacheProduct(action.product)
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
                checkoutViewModel.addToCart(action.product)
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
    appParamaters: ShopParameters,
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