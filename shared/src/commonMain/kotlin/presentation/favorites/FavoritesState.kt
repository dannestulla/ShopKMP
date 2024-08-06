package presentation.favorites

import data.model.Product
import presentation.ProductUI


data class FavoritesState(val products: List<ProductUI>? = null)
