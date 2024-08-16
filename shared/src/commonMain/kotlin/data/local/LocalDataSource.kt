package data.local

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import br.gohan.shopsample.database.ShopSampleDatabase
import data.model.Product
import database.Checkout
import database.Favorites
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.Clock
import presentation.model.CheckoutUI
import presentation.model.ProductUI

class LocalDataSource(
    database: ShopSampleDatabase,
) {
    private val favoritesTable = database.favoritesQueries
    private val checkoutTable = database.checkoutQueries

    fun getFavorites(): Flow<List<Favorites>> {
        return favoritesTable.getFavorites()
            .asFlow()
            .mapToList(Dispatchers.IO)

    }

    fun getFavoriteByTitle(title: String): Favorites? {
        return favoritesTable.getFavoriteTitle(title).executeAsList().firstOrNull()
    }

    fun saveFavorite(product: Product) {
        favoritesTable.saveFavorite(
            title = product.title,
            price = product.price.toString(),
            description = product.description,
            image = product.images.first(),
            timestamp = Clock.System.now().toEpochMilliseconds(),
            category = product.category.name
        )
    }

    fun removeFavorite(product: Product) {
        val favorite = favoritesTable.getFavoriteTitle(product.title).executeAsList()
        favorite.forEach {
            favoritesTable.removeFavoriteById(it.id)
        }
    }

    fun addToCheckout(product: ProductUI) {
        checkoutTable.addToCheckout(
            title = product.title,
            price = product.newPrice,
            oldPrice = product.oldPrice,
            description = product.description,
            image = product.images.firstOrNull() ?: "",
            category = product.category,
            timestamp = Clock.System.now().toEpochMilliseconds(),
            sizeSelected = product.sizeSelected ?: "40"
        )
    }

    fun getCheckoutItems(): Flow<List<Checkout>> {
        return checkoutTable
            .getCheckoutItems()
            .asFlow()
            .mapToList(Dispatchers.IO)
    }

    fun removeFromCheckout(checkoutItem: CheckoutUI) {
        checkoutTable.getCartItemById(checkoutItem.title).executeAsList().let { product ->
            product.forEach {
                checkoutTable.removeFromCheckout(it.id)
            }
        }
    }
}