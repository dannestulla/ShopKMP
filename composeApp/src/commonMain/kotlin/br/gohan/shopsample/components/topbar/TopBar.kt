package br.gohan.shopsample.components.topbar

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.navigation.NavHostController
import br.gohan.shopsample.AppRoutes
import br.gohan.shopsample.ui.Dimens
import presentation.favorites.SharedFavoritesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    state: TopBarState,
    action: (TopBarAction) -> Unit
) = with(state) {
    var favorite by remember { mutableStateOf(state.isFavorited) }
    var searchActive by remember {
        mutableStateOf(isSearchActive)
    }
    var search by remember { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    TopAppBar(
        title = {
            if (searchActive) {
                LaunchedEffect(Unit) {
                    focusRequester.requestFocus()
                }
                TextField(
                    modifier = Modifier.focusRequester(focusRequester = focusRequester),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White
                    ),
                    textStyle = TextStyle(fontSize = Dimens.fontMedium),
                    value = search, onValueChange = {
                        action(TopBarAction.Search(it))
                        search = it
                    })
                return@TopAppBar
            } else {
                Text(title, fontSize = Dimens.fontLarge)
            }
        },
        navigationIcon = {
            IconButton(onClick = {
                if (searchActive) {
                    searchActive = !searchActive
                    return@IconButton
                }
                action(TopBarAction.Back)
            }) {
                Icon(Icons.AutoMirrored.Default.KeyboardArrowLeft, contentDescription = "Back")
            }
        },
        actions = {
            if (hasFavoriteButton) {
                IconToggleButton(
                    checked = favorite,
                    onCheckedChange = {
                        favorite = !favorite
                    }) {
                    Icon(Icons.Default.FavoriteBorder, contentDescription = "Favorite")
                }
            }
            if (searchActive) {
                search = ""
                IconButton(onClick = { searchActive = !searchActive }) {
                    Icon(Icons.Default.Close, contentDescription = "Close")
                }
            } else {
                focusManager.clearFocus()
                action(TopBarAction.CancelSearch)
                IconButton(onClick = { searchActive = !searchActive }) {
                    Icon(Icons.Default.Search, contentDescription = "Search")
                }
            }
        })
}

data class TopBarState(
    var title: String,
    val hasFavoriteButton: Boolean = false,
    val isFavorited: Boolean = false,
    val isSearchActive: Boolean = false,
)

internal fun TopBarAction.handle(
    navController: NavHostController,
    topBarState: TopBarState,
    favoritesViewModel: SharedFavoritesViewModel,
    currentSearch: String?,
    selectedRoute: AppRoutes,
    searchActive: (String?) -> Unit
) {
    when (this) {
        is TopBarAction.Back -> {
            navController.popBackStack()
            topBarState.title = setTopTitle(selectedRoute.name)
        }

        is TopBarAction.Favorite -> {
            favoritesViewModel.saveFavorite(this.product)
        }

        is TopBarAction.Search -> {
            searchActive(this.search)
        }

        is TopBarAction.CancelSearch -> {
            searchActive(null)
        }
    }
}

