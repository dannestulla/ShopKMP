package presentation

import data.ShopRepository
import domain.mappers.toProduct
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import presentation.model.FavoritesState
import presentation.model.ProductUI

class FavoritesViewModel(
    private val repository: ShopRepository,
    private val viewModelScope: CoroutineScope
) : KoinComponent {

    private val _state = MutableStateFlow(FavoritesState())
    val state = _state.asStateFlow()

    init {
        getFavorites()
    }

    private fun getFavorites() {
        viewModelScope.launch {
            repository.getFavorites().collect { favorites ->
                _state.update {
                    FavoritesState(favorites.toProduct())
                }
            }
        }
    }

    fun removeFavorite(product: ProductUI) {
        viewModelScope.launch {
            repository.removeFavorite(product)
        }
    }
}
