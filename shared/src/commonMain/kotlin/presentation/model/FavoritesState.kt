package presentation.model

import kotlin.jvm.JvmInline


@JvmInline
value class FavoritesState(val products: List<ProductUI>? = null)
