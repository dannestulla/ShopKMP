package br.gohan.presenter.components

import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import br.gohan.AppRoutes

@Composable
fun BottomNavBar(selected: AppRoutes, callback: (AppRoutes) -> Unit) {
    return BottomAppBar {
        BottomNavigationItem(
            selected = selected == AppRoutes.CATEGORIES,
            onClick = {
                callback.invoke(AppRoutes.CATEGORIES)
                      },
            label = {
                Text(
                    text = "Categories",
                )
            },
            icon = {
                if (selected == AppRoutes.CATEGORIES) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription =  "Products Icon",
                    )
                } else {
                    Icon(
                        imageVector = Icons.Outlined.Search,
                        contentDescription =  "Products Icon",
                    )
                }
            }
        )
        BottomNavigationItem(
            selected = selected == AppRoutes.FAVORITES,
            onClick = { callback.invoke(AppRoutes.FAVORITES) },
            label = {
                Text(
                    text = "Favorites",
                )
            },
            icon = {
                if (selected == AppRoutes.FAVORITES) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "ShoppingCard Icon",
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.FavoriteBorder,
                        contentDescription = "ShoppingCard Icon",
                    )
                }
            }
        )
        BottomNavigationItem(
            selected = selected == AppRoutes.CHECKOUT,
            onClick = { callback.invoke(AppRoutes.CHECKOUT) },
            label = {
                Text(
                    text = "Checkout",
                )
            },
            icon = {
                if (selected == AppRoutes.CHECKOUT) {
                    Icon(
                        imageVector = Icons.Default.ShoppingCart,
                        contentDescription = "ShoppingCard Icon",
                    )
                } else {
                    Icon(
                        imageVector = Icons.Outlined.ShoppingCart,
                        contentDescription = "ShoppingCard Icon",
                    )
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun BottomNavBarPreview() {
    BottomNavBar(AppRoutes.CHECKOUT) {

    }
}