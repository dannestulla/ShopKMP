package data

import data.local.LocalDataSource
import data.model.Categories
import data.remote.RemoteDataSource
import database.Checkout
import database.Favorites
import domain.CURRENT_DISCOUNT
import domain.mappers.toProductUI
import kotlinx.coroutines.flow.Flow
import presentation.model.CheckoutUI
import presentation.model.ProductUI


class ShopRepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : ShopRepository {
    override suspend fun getProducts(): List<ProductUI> {
        return remoteDataSource.getProducts().toProductUI(CURRENT_DISCOUNT)
    }

    override suspend fun getCategories(): List<Categories> {
        return remoteDataSource.getCategories()
    }

    override fun getFavorites(): Flow<List<Favorites>> {
        return localDataSource.getFavorites()
    }

    override fun getFavoriteByTitle(title: String): Favorites? {
        return localDataSource.getFavoriteByTitle(title)
    }

    override fun saveFavorite(product: ProductUI, discount: Double) {
        return localDataSource.saveFavorite(product, discount)
    }

    override fun removeFavorite(product: ProductUI) {
        localDataSource.removeFavorite(product)
    }

    override fun addToCheckout(product: ProductUI) {
        localDataSource.addToCheckout(product)
    }

    override fun getCheckoutItems(): Flow<List<Checkout>> {
        return localDataSource.getCheckoutItems()
    }

    override fun removeFromCheckout(checkoutItem: CheckoutUI) {
        localDataSource.removeFromCheckout(checkoutItem)
    }
}