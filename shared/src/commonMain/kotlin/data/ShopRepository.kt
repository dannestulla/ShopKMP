package data

import br.gohan.shopsample.database.ShopSampleDatabase
import data.model.Category
import data.model.Categories
import data.model.Product
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.datetime.Clock

class ShopRepository(
    private val httpClient: HttpClient,
    database: ShopSampleDatabase

) {
    private val baseUrl = "https://api.escuelajs.co/api/v1"
    private val favoritesTable = database.favoritesQueries
    private val checkoutTable = database.checkoutQueries

    suspend fun getProducts(): List<Product> {
        return httpClient.get("$baseUrl/products").body()
    }

    suspend fun getCategories(): List<Categories> {
        return httpClient.get("$baseUrl/categories").body<List<Categories>>().subList(0, 5)
        // Sublist to avoid weird categories from API
    }

    fun getFavorites() : List<Product> {
        return favoritesTable.getFavorites().executeAsList().map {
            Product(
                title = it.title,
                price = it.price.toDouble(),
                description = it.description,
                images = listOf(it.image),
                category = Category(name = it.category)
            )
        }
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
        val favorite = favoritesTable.getFavoriteById(product.title).executeAsList()
        favorite.forEach {
            favoritesTable.removeFavoriteById(it.id)
        }
    }

    fun addItemToCart(product: Product) {

    }

    fun removeItemFromCart(product: Product) {

    }
}