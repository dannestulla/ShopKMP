package presentation.checkout

import data.ShopRepository
import domain.mappers.toCheckoutUI
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import presentation.CoroutineViewModel
import presentation.products.ProductUI


class CheckoutViewModel : CoroutineViewModel(), KoinComponent {
    private val repository by inject<ShopRepository>()

    private val viewModelScope = coroutineScope

    private val _state = MutableStateFlow(CheckoutState(null))
    val state = _state.asStateFlow()

    fun getItems() {
        viewModelScope.launch {
            _state.update {
                CheckoutState(
                    repository.getCheckoutItems().map {
                        it.toCheckoutUI()
                    }
                )
            }
        }
    }

    fun addToCart(product: ProductUI) {
        repository.addToCheckout(product)
    }
}