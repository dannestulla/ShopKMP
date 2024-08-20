package domain.mappers

import data.model.Category
import data.model.Product
import domain.currencyToDouble
import domain.setDiscount
import domain.toCurrency
import domain.toPercentage
import presentation.model.ProductUI

internal fun List<Product>.toProductUI(discount: Double): List<ProductUI> {
    return this.map {
        ProductUI(
            it.title,
            it.price.toCurrency(),
            it.price.setDiscount(discount),
            discount.toPercentage(),
            it.description,
            it.images,
            it.category.name,

            )
    }
}

internal fun ProductUI.toProduct(): Product {
    return Product(
        this.title,
        this.oldPrice.currencyToDouble(),
        this.description,
        this.images,
        Category(this.category)
    )
}