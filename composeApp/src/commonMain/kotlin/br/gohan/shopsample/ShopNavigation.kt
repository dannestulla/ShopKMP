package br.gohan.shopsample

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import br.gohan.shopsample.components.topbar.TopBarState
import br.gohan.shopsample.screens.CategoriesScreen
import br.gohan.shopsample.screens.CheckoutScreen
import br.gohan.shopsample.screens.FavoritesScreen
import br.gohan.shopsample.screens.ProductScreen
import br.gohan.shopsample.screens.ProductsScreen
import kotlinx.coroutines.CoroutineScope

@Composable
fun ShopNavigation(
    shopParameters: ShopParameters,

) = with(shopParameters) {
    NavHost(
        navController = navController,
        startDestination = AppRoutes.CATEGORIES.name
    ) {
        composable(route = AppRoutes.CATEGORIES.name) {
            CategoriesScreen(currentSearch, paddingValues) { category ->
                navController.navigate(AppRoutes.PRODUCTS.name + "/$category")
            }
        }
        composable(route = AppRoutes.FAVORITES.name) {
            FavoritesScreen(
                shopParameters
            )
        }
        composable(route = AppRoutes.CHECKOUT.name) {
            CheckoutScreen(paddingValues)
        }

        val categoryArg = "category"
        composable(
            route = AppRoutes.PRODUCTS.name + "/{$categoryArg}",
            arguments = listOf(
                navArgument(categoryArg) {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            backStackEntry.arguments?.getString(categoryArg).let { category ->
                ProductsScreen(currentSearch, shopParameters, category!!)
            }
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
    val dataStoreManager: DataStoreManager,
    val paddingValues: PaddingValues,
    val snackbar: SnackbarHostState,
    val coroutine: CoroutineScope
)