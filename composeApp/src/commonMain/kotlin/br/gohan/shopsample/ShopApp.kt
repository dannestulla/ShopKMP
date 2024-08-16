package br.gohan.shopsample

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
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
import br.gohan.shopsample.components.topbar.TopBarAction
import br.gohan.shopsample.components.topbar.TopBarState
import br.gohan.shopsample.components.topbar.setTopTitle
import br.gohan.shopsample.screens.BottomSheet
import br.gohan.shopsample.ui.Dimens
import br.gohan.shopsample.ui.ShopTheme
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import shopsample.composeapp.generated.resources.Res
import shopsample.composeapp.generated.resources.shopping_cart_checkout

@Composable
fun ShopApp(dataStoreManager: DataStoreManager) {
    ShopTheme {
        val navController = rememberNavController()
        val snackBar = SnackbarHostState()
        val coroutine = rememberCoroutineScope()

        val backStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = backStackEntry?.destination

        var showBottomSheet by remember { mutableStateOf(false) }

        if (showBottomSheet) {
            BottomSheet {
                showBottomSheet = it
            }
        }

        var selectedRoute by remember {
            mutableStateOf(
                (backStackEntry?.destination?.route
                    ?: AppRoutes.CATEGORIES.name).getRoute()
            )
        }

        LaunchedEffect(currentDestination) {
            currentDestination?.let {
                selectedRoute = it.route?.getRoute() ?: AppRoutes.CATEGORIES
            }
        }

        var currentSearch by remember {
            mutableStateOf<String?>(null)
        }

        val topBarState by derivedStateOf {
            TopBarState(
                setTopTitle(selectedRoute.name),
                isSearchActive = currentSearch != null,
                noSearch = selectedRoute == AppRoutes.PRODUCT || selectedRoute == AppRoutes.CHECKOUT
            )
        }

        Scaffold(
            floatingActionButton = {
                if (selectedRoute == AppRoutes.CHECKOUT) {
                    FloatingActionButton(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                        onClick = {
                            coroutine.launch {
                                showBottomSheet = true
                            }
                        }) {
                        Image(
                            painterResource(Res.drawable.shopping_cart_checkout),
                            "Finish checkout"
                        )
                    }
                }
            },
            snackbarHost = { SnackbarHost(snackBar) },
            topBar = {
                TopBar(
                    topBarState
                ) { action ->
                    when (action) {
                        is TopBarAction.Back -> {
                            navController.popBackStack()
                        }

                        is TopBarAction.Search -> {
                            currentSearch = action.search
                        }

                        is TopBarAction.CancelSearch -> {
                            currentSearch = null
                        }
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
                        dataStoreManager,
                        paddingValues,
                        snackBar,
                        coroutine
                    )
                )
            }
        }
    }
}
