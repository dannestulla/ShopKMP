package domain.mappers

import database.Checkout
import presentation.model.CheckoutUI

internal fun Checkout.toCheckoutUI(): CheckoutUI {
    return CheckoutUI(
        this.title,
        this.price,
        this.oldPrice,
        this.description,
        this.image,
        this.category,
        this.sizeSelected
    )
}
