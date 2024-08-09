package br.gohan.shopsample

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import br.gohan.shopsample.components.ProductAction
import br.gohan.shopsample.components.topbar.TopBarState
import br.gohan.shopsample.components.topbar.setTopTitle
import br.gohan.shopsample.screens.CategoriesScreen
import br.gohan.shopsample.screens.CheckoutScreen
import br.gohan.shopsample.screens.FavoritesScreen
import br.gohan.shopsample.screens.ProductScreen
import br.gohan.shopsample.screens.ProductsScreen
import io.ktor.http.ContentType.Application.Json
import presentation.categories.SharedCategoriesViewModel
import presentation.favorites.SharedFavoritesViewModel
import presentation.items.ProductsViewModel
import presentation.model.ProductUI

@Composable
fun ShopNavHost(
    navHostState: ShopNavHostState
) = with(navHostState) {
    NavHost(
        navController = navController,
        startDestination = AppRoutes.CATEGORIES.name
    ) {
        composable(route = AppRoutes.CATEGORIES.name) {
            val categoriesViewModel = remember { SharedCategoriesViewModel() }
            val state = categoriesViewModel.state.collectAsState()
            CategoriesScreen(state.value.categories, currentSearch) {
                topBarState.title = it
                navController.navigate(AppRoutes.PRODUCTS.name)
            }
        }
        composable(route = AppRoutes.FAVORITES.name) {
            favoritesViewModel.getFavorites()
            val state = favoritesViewModel.state.collectAsState()
            FavoritesScreen(state.value.products) { action ->
                when (action) {
                    is ProductAction.Navigate -> {
                        //navController.navigate(action.product)
                    }

                    is ProductAction.Favorite -> {
                        favoritesViewModel.saveFavorite(action.product)
                    }

                    is ProductAction.RemoveFavorite -> {
                        favoritesViewModel.removeFavorite(action.product)
                    }
                }
            }

        }
        composable(route = AppRoutes.CHECKOUT.name) {
            CheckoutScreen()
        }

        composable(route = AppRoutes.PRODUCTS.name) {
            val products = remember { ProductsViewModel() }
            val state = products.state.collectAsState()
            ProductsScreen(state.value.products, currentSearch) { action ->
                when (action) {
                    is ProductAction.Navigate -> {
                        topBarState.title = setTopTitle(AppRoutes.PRODUCT.name)
                        //navController.navigate(action.product)
                    }

                    is ProductAction.Favorite -> {
                        favoritesViewModel.saveFavorite(action.product)
                    }

                    is ProductAction.RemoveFavorite -> {
                        favoritesViewModel.removeFavorite(action.product)
                    }
                }
            }
        }
        composable(route = AppRoutes.PRODUCT.name) {
            val gson: Gson = Json GsonBuilder().create()
            val userJson = it.arguments?.getString("user")
            val userObject = gson.fromJson(userJson, ProductUI::class)
            ProductScreen(userObject) {

            }
        }
    }
}

data class ShopNavHostState(
    val navController: NavHostController,
    val currentSearch: String?,
    val topBarState: TopBarState,
    val favoritesViewModel : SharedFavoritesViewModel,
)