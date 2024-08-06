package domain

import java.text.NumberFormat
import java.util.Locale

actual fun Double.toCurrency(): String {
    return NumberFormat.getCurrencyInstance(Locale("pt", "BR")).format(this)
}

actual fun Double.toPercentage(): String {
    return NumberFormat.getPercentInstance().apply {
        maximumFractionDigits = 2
    }.format(this)
}