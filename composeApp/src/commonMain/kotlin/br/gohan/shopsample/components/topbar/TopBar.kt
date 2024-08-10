package br.gohan.shopsample.components.topbar

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.IconToggleButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
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
import androidx.navigation.NavHostController
import br.gohan.shopsample.AppRoutes
import org.jetbrains.compose.ui.tooling.preview.Preview
import presentation.favorites.SharedFavoritesViewModel

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
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        backgroundColor = Color.White,
                        cursorColor = Color.Black
                    ),
                    value = search, onValueChange = {
                        action(TopBarAction.Search(it))
                        search = it
                    })
                return@TopAppBar
            } else {
                Text(title)
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
    currentSearch: MutableState<String?>,
    selectedRoute: AppRoutes,

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
            currentSearch.value = this.search
        }

        is TopBarAction.CancelSearch -> {
            currentSearch.value = null
        }
    }
}

