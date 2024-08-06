package presentation.favorites

import data.ShopRepository
import data.model.Product
import domain.CURRENT_DISCOUNT
import domain.toCurrency
import domain.toProductUI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import presentation.CoroutineViewModel
import presentation.ProductUI

class SharedFavoritesViewModel() : CoroutineViewModel(), KoinComponent {
    private val repository by inject<ShopRepository>()

    private val viewModelScope = coroutineScope

    private val _state = MutableStateFlow(FavoritesState())
    val state = _state.asStateFlow()

    init {
        getFavorites()
    }

    private fun getFavorites() {
        viewModelScope.launch {
            _state.update {
                FavoritesState(
                    repository.getFavorites().map {
                        it.toProductUI(CURRENT_DISCOUNT)
                    }
                )
            }
        }
    }
}