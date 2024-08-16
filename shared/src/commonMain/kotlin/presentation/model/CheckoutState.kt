package presentation.model

import kotlin.jvm.JvmInline


@JvmInline
value class CheckoutState(
    val checkoutItems: List<CheckoutUI>? = null
)
