package data

import br.gohan.shopsample.database.FavoritesDatabase
import data.model.Category
import data.model.Categories
import data.model.Product
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.datetime.Clock

class ShopRepository(
    private val httpClient: HttpClient,
    database: FavoritesDatabase

) {
    private val baseUrl = "https://api.escuelajs.co/api/v1"
    private val dbQueries = database.favoritesQueries


    suspend fun getProducts(): List<Product> {
        return httpClient.get("$baseUrl/products").body()
    }

    suspend fun getCategories(): List<Categories> {
        return httpClient.get("$baseUrl/categories").body()
    }

    fun getFavorites() : List<Product> {
        return dbQueries.getFavorites().executeAsList().subList(0, 5).map {
            Product(
                id = it.id.toInt(),
                title = it.title,
                price = it.price.toDouble(),
                description = it.description,
                images = listOf(it.image),
                category = Category(name = it.category)
            )
        }
    }

    fun saveFavorite(favorite: Product) {
        dbQueries.saveFavorite(
            id = favorite.id.toLong(),
            title = favorite.title,
            price = favorite.price.toString(),
            description = favorite.description,
            image = favorite.images.first(),
            timestamp = Clock.System.now().toEpochMilliseconds(),
            category = favorite.category.name
        )
    }
}