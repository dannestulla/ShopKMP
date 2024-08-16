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


class CheckoutViewModel : CoroutineViewModel(), KoinComponent {
    private val repository by inject<ShopRepository>()

    private val viewModelScope = coroutineScope

    private val _state = MutableStateFlow(CheckoutState(null))
    val state = _state.asStateFlow()

    init {
        getItems()
    }

    private fun getItems() {
        viewModelScope.launch {
            repository.getCheckoutItems().collect { items ->
                _state.update {
                    items.map { item ->
                        item.toCheckoutUI()
                    }.let { result ->
                        CheckoutState(result)
                    }
                }
            }
        }
    }

    fun removeFromCheckout(product: CheckoutUI) {
        repository.removeFromCheckout(product)
    }
}