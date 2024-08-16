package domain.mappers

import data.model.Category
import data.model.Product
import database.Favorites

fun Favorites.toProduct(): Product =
    Product(
        title = this.title,
        price = this.price.toDouble(),
        description = this.description,
        images = listOf(this.image),
        category = Category(name = this.category)
    )

