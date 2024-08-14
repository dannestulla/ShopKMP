package presentation.checkout

import kotlin.jvm.JvmInline


@JvmInline
value class CheckoutState(
    val checkoutItems: List<CheckoutUI>? = null
)
