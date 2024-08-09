package br.gohan.presenter

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import br.gohan.shopsample.components.BottomNavBar
import br.gohan.shopsample.components.topbar.TopBar
import br.gohan.shopsample.components.topbar.TopBarState
import br.gohan.shopsample.components.topbar.handle
import br.gohan.shopsample.components.topbar.setTopTitle
import br.gohan.shopsample.ui.ShopTheme
import presentation.favorites.SharedFavoritesViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import br.gohan.shopsample.AppRoutes
import br.gohan.shopsample.ShopNavHost
import br.gohan.shopsample.ShopNavHostState
import br.gohan.shopsample.getRoute


@Composable
fun ShopApp() {
    ShopTheme {
        val navController = rememberNavController()
        val backStackEntry = navController.currentBackStackEntryAsState()

        var selectedRoute by remember {
            mutableStateOf((backStackEntry.value?.destination?.route
                ?: AppRoutes.CATEGORIES.name).getRoute())
        }

        val currentSearch = remember {
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

        val favoritesViewModel = remember { SharedFavoritesViewModel() }

        Scaffold(
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
                    )
                }
            },
            modifier = Modifier.fillMaxSize(),
            bottomBar = {
                BottomNavBar(
                    selectedRoute
                ) {
                    selectedRoute = it
                    navController.navigate(it.name)
                }
            }
        ) { _ ->
            Surface(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                ShopNavHost(
                    ShopNavHostState(
                        navController,
                        currentSearch.value,
                        topBarState,
                        favoritesViewModel,
                    )
                )
            }
        }
    }
}
