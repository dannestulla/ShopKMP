package br.gohan.shopsample.components.topbar

import br.gohan.shopsample.AppRoutes

internal fun setTopTitle(route: String?) : String {
    return when (route) {
        AppRoutes.CATEGORIES.name -> "Categories"
        AppRoutes.FAVORITES.name -> "Favorites"
        AppRoutes.CHECKOUT.name -> "Checkout"
        AppRoutes.PRODUCTS.name -> "Products"
        AppRoutes.PRODUCT.name -> "Product"
        else -> "Product"
    }
}

sealed class TopBarAction {
    data class Search(val search: String?) : TopBarAction()
    data object Back : TopBarAction()
    data object CancelSearch : TopBarAction()
}

