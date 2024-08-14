package presentation.favorites

import presentation.products.ProductUI
import kotlin.jvm.JvmInline


@JvmInline
value class FavoritesState(val products: List<ProductUI>? = null)
