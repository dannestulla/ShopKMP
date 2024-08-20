package presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.ShopRepositoryImpl
import domain.mappers.toCheckoutUI
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import presentation.model.CheckoutState
import presentation.model.CheckoutUI


class CheckoutViewModel(
    private val repository: ShopRepositoryImpl
) : ViewModel(), KoinComponent {

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