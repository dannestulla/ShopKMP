package br.gohan.shopsample


enum class AppRoutes {
    CATEGORIES,
    PRODUCTS,
    PRODUCT,
    FAVORITES,
    CHECKOUT
}

fun String.getRoute(): AppRoutes {
    val route = this.split("/").first()
    return when (route) {
        "CATEGORIES" -> AppRoutes.CATEGORIES
        "PRODUCTS" -> AppRoutes.PRODUCTS
        "PRODUCT" -> AppRoutes.PRODUCT
        "FAVORITES" -> AppRoutes.FAVORITES
        "CHECKOUT" -> AppRoutes.CHECKOUT
        else -> throw IllegalArgumentException("Invalid route: $this")
    }
}