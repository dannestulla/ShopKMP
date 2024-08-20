package domain.mappers

import database.Favorites
import presentation.model.ProductUI

fun List<Favorites>.toProduct(): List<ProductUI> =
    this.map {
        ProductUI(
            title = it.title,
            oldPrice = it.oldPrice,
            newPrice = it.newPrice,
            description = it.description,
            images = listOf(it.image),
            discount = it.discount,
            category = it.category
        )
    }
