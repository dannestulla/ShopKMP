package data

import data.model.Categories
import database.Checkout
import database.Favorites
import kotlinx.coroutines.flow.Flow
import presentation.model.CheckoutUI
import presentation.model.ProductUI

interface ShopRepository {
    suspend fun getProducts(): List<ProductUI>

    suspend fun getCategories(): List<Categories>

    fun getFavorites(): Flow<List<Favorites>>

    fun getFavoriteByTitle(title: String): Favorites?

    fun saveFavorite(product: ProductUI, discount: Double)

    fun removeFavorite(product: ProductUI)

    fun addToCheckout(product: ProductUI)

    fun getCheckoutItems(): Flow<List<Checkout>>

    fun removeFromCheckout(checkoutItem: CheckoutUI)
}