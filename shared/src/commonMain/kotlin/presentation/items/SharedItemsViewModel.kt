package presentation.items

import data.ShopRepository
import domain.CURRENT_DISCOUNT
import domain.setDiscount
import domain.toCurrency
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
import presentation.favorites.FavoritesState


class SharedItemsViewModel() : CoroutineViewModel(), KoinComponent {
    private val repository by inject<ShopRepository>()

    private val viewModelScope = coroutineScope

    private val _state = MutableStateFlow(FavoritesState())
    val state = _state.asStateFlow()

    init {
        getItems()
    }

    private fun getItems() {
        viewModelScope.launch {
            _state.update {
                FavoritesState(
                    repository.getProducts().map {
                        ProductUI(
                            it.title,
                            it.price.toCurrency(),
                            it.price.setDiscount(CURRENT_DISCOUNT),
                            it.description,
                            it.description,
                            it.images,
                            it.category.name
                        )
                    }
                )
            }
        }
    }
}