package br.gohan.shopsample

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import br.gohan.shopsample.components.BottomNavBar
import br.gohan.shopsample.components.topbar.TopBar
import br.gohan.shopsample.components.topbar.TopBarState
import br.gohan.shopsample.components.topbar.handle
import br.gohan.shopsample.components.topbar.setTopTitle
import br.gohan.shopsample.ui.Dimens
import br.gohan.shopsample.ui.ShopTheme
import presentation.checkout.CheckoutViewModel
import presentation.favorites.FavoritesViewModel
import presentation.products.ProductsViewModel


@Composable
fun ShopApp(dataStoreManager: DataStoreManager) {
    ShopTheme {
        val navController = rememberNavController()
        val backStackEntry = navController.currentBackStackEntryAsState()
        val snackbar = SnackbarHostState()

        var selectedRoute by remember {
            mutableStateOf(
                (backStackEntry.value?.destination?.route
                    ?: AppRoutes.CATEGORIES.name).getRoute()
            )
        }

        var currentSearch by remember {
            mutableStateOf<String?>(null)
        }

        val topBarState by remember {
            mutableStateOf(
                TopBarState(
                    setTopTitle(selectedRoute.name),
                    isSearchActive = currentSearch != null,
                    hasFavoriteButton = selectedRoute == AppRoutes.PRODUCT
                )
            )
        }

        val favoritesViewModel = remember { FavoritesViewModel() }
        val productsViewModel = remember { ProductsViewModel() }
        val checkoutViewModel = remember { CheckoutViewModel() }
        val coroutine = rememberCoroutineScope()

        Scaffold(
            snackbarHost = { SnackbarHost(snackbar) },
            topBar = {
                TopBar(
                    topBarState
                ) { action ->
                    action.handle(
                        navController,
                        topBarState,
                        favoritesViewModel,
                        currentSearch,
                        selectedRoute
                    ) {
                        currentSearch = it
                    }
                }
            },
            bottomBar = {
                BottomNavBar(
                    selectedRoute
                ) {
                    selectedRoute = it
                    navController.navigate(it.name)
                }
            }
        ) { paddingValues ->
            Surface(
                modifier = Modifier.padding(horizontal = Dimens.paddingLarge),
            ) {
                ShopNavigation(
                    ShopParameters(
                        navController,
                        currentSearch,
                        topBarState,
                        favoritesViewModel,
                        dataStoreManager,
                        paddingValues,
                        snackbar,
                        coroutine,
                        productsViewModel,
                        checkoutViewModel
                    )
                )
            }
        }
    }
}
