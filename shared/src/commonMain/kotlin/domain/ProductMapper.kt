package domain

import data.model.Product
import presentation.ProductUI

internal fun Product.toProductUI(discount: Double): ProductUI {
    return ProductUI(
        this.title,
        this.price.toCurrency(),
        this.price.setDiscount(discount),
        this.description,
        this.description,
        this.images,
        this.category.name
    )
}