package br.gohan.shopsample


enum class AppRoutes {
    CATEGORIES,
    PRODUCTS,
    PRODUCT,
    FAVORITES,
    CHECKOUT
}

fun String.getRoute(): AppRoutes {
    return when (this) {
        "CATEGORIES" -> AppRoutes.CATEGORIES
        "PRODUCTS" -> AppRoutes.PRODUCTS
        "PRODUCT" -> AppRoutes.PRODUCT
        "FAVORITES" -> AppRoutes.FAVORITES
        "CHECKOUT" -> AppRoutes.CHECKOUT
        else -> AppRoutes.FAVORITES
    }
}