package data

import data.local.LocalDataSource
import data.model.Categories
import data.model.Product
import data.remote.RemoteDataSource
import database.Checkout
import database.Favorites
import kotlinx.coroutines.flow.Flow
import presentation.model.CheckoutUI
import presentation.model.ProductUI

class ShopRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource

) {
    suspend fun getProducts(): List<Product> {
        return remoteDataSource.getProducts()
    }

    suspend fun getCategories(): List<Categories> {
        return remoteDataSource.getCategories()
    }

    fun getFavorites(): Flow<List<Favorites>> {
        return localDataSource.getFavorites()
    }

    fun getFavoriteByTitle(title: String): Favorites? {
        return localDataSource.getFavoriteByTitle(title)
    }

    fun saveFavorite(product: Product) {
        return localDataSource.saveFavorite(product)
    }

    fun removeFavorite(product: Product) {
        localDataSource.removeFavorite(product)
    }

    fun addToCheckout(product: ProductUI) {
        localDataSource.addToCheckout(product)
    }

    fun getCheckoutItems(): Flow<List<Checkout>> {
        return localDataSource.getCheckoutItems()
    }

    fun removeFromCheckout(checkoutItem: CheckoutUI) {
        localDataSource.removeFromCheckout(checkoutItem)
    }
}