package domain

import data.model.Category
import data.model.Product
import presentation.model.ProductUI

internal fun Product.toProductUI(discount: Double): ProductUI {
    return ProductUI(
        this.title,
        this.price.toCurrency(),
        this.price.setDiscount(discount),
        discount.toPercentage(),
        this.description,
        this.images,
        this.category.name,

    )
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