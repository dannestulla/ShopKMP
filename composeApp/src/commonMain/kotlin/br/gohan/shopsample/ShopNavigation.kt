package br.gohan.shopsample

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import br.gohan.shopsample.components.topbar.TopBarState
import br.gohan.shopsample.screens.CategoriesScreen
import br.gohan.shopsample.screens.CheckoutScreen
import br.gohan.shopsample.screens.FavoritesScreen
import br.gohan.shopsample.screens.ProductScreen
import br.gohan.shopsample.screens.ProductsScreen
import kotlinx.coroutines.CoroutineScope
import presentation.categories.CategoriesViewModel
import presentation.checkout.CheckoutViewModel
import presentation.favorites.FavoritesViewModel
import presentation.products.ProductsViewModel

@Composable
fun ShopNavigation(
    shopParameters: ShopParameters,
) = with(shopParameters) {
    NavHost(
        navController = navController,
        startDestination = AppRoutes.CATEGORIES.name
    ) {
        composable(route = AppRoutes.CATEGORIES.name) {
            val categoriesViewModel = remember { CategoriesViewModel() }
            val state = categoriesViewModel.state.collectAsState()
            CategoriesScreen(state.value.categories, currentSearch, paddingValues) {
                topBarState.title = it
                navController.navigate(AppRoutes.PRODUCTS.name)
            }
        }
        composable(route = AppRoutes.FAVORITES.name) {
            favoritesViewModel.getFavorites()
            val state = favoritesViewModel.state.collectAsState()
            FavoritesScreen(
                state.value.products,
                shopParameters
            )
        }
        composable(route = AppRoutes.CHECKOUT.name) {
            CheckoutScreen(paddingValues, checkoutViewModel) {

            }
        }

        composable(route = AppRoutes.PRODUCTS.name) {
            ProductsScreen(currentSearch, shopParameters)
        }
        composable(
            route = AppRoutes.PRODUCT.name
        ) {
            ProductScreen(shopParameters)
        }
    }
}

data class ShopParameters(
    val navController: NavHostController,
    val currentSearch: String?,
    val topBarState: TopBarState,
    val favoritesViewModel: FavoritesViewModel,
    val dataStoreManager: DataStoreManager,
    val paddingValues: PaddingValues,
    val snackbar: SnackbarHostState,
    val coroutine: CoroutineScope,
    val productsViewModel: ProductsViewModel,
    val checkoutViewModel: CheckoutViewModel
)