package br.gohan

import br.gohan.presenter.ui.ShopTheme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import br.gohan.presenter.components.BottomNavBar
import br.gohan.presenter.screens.CheckoutScreen
import br.gohan.presenter.screens.LoadingScreen
import br.gohan.presenter.screens.CategoriesScreen
import br.gohan.presenter.screens.FavoritesScreen
import br.gohan.presenter.screens.ProductsScreen
import br.gohan.presenter.screens.ProductScreen
import presentation.ProductUI
import presentation.categories.SharedCategoriesViewModel
import presentation.favorites.SharedFavoritesViewModel
import presentation.items.SharedItemsViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShopTheme {
                val navController = rememberNavController()
                val backStackEntry = navController.currentBackStackEntryAsState()
                val selectedRoute =
                    (backStackEntry.value?.destination?.route
                        ?: AppRoutes.CATEGORIES.name).getRoute()
                Scaffold(
                    topBar = {
                        TopAppBar {

                        }
                    },
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        BottomNavBar(selectedRoute) {
                            navController.navigate(it.name)
                        }
                    }
                ) { _ ->
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .fillMaxWidth()
                    ) {
                        NavHost(
                            navController = navController,
                            startDestination = AppRoutes.CATEGORIES.name
                        ) {
                            composable(route = AppRoutes.CATEGORIES.name) {
                                val categoriesViewModel = remember { SharedCategoriesViewModel() }
                                val state = categoriesViewModel.state.collectAsState()
                                if (state.value.categories == null) {
                                    LoadingScreen()
                                } else {
                                    CategoriesScreen(state.value.categories!!) {
                                        navController.navigate(AppRoutes.PRODUCTS.name)
                                    }
                                }
                            }
                            composable(route = AppRoutes.FAVORITES.name) {
                                val favoriteViewModel = remember { SharedFavoritesViewModel() }
                                val state = favoriteViewModel.state.collectAsState()
                                if (state.value.products == null) {
                                    LoadingScreen()
                                } else {
                                    FavoritesScreen(state.value.products!!)
                                }
                            }
                            composable(route = AppRoutes.CHECKOUT.name) {
                                CheckoutScreen()

                            }

                            composable(route = AppRoutes.PRODUCTS.name) {
                                val sharedItemsViewModel = remember { SharedItemsViewModel() }
                                val state = sharedItemsViewModel.state.collectAsState()
                                if (state.value.products == null) {
                                    LoadingScreen()
                                } else {
                                    ProductsScreen(state.value.products!!) { product ->
                                        navController.navigate(product)
                                    }
                                }
                            }
                            composable<ProductUI> {
                                val args = it.toRoute<ProductUI>()
                                ProductScreen(args)
                            }
                        }
                    }
                }
            }
        }
    }
}
