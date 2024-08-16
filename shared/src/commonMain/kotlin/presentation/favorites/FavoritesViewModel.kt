package presentation.favorites

import data.ShopRepository
import data.model.Product
import domain.CURRENT_DISCOUNT
import domain.mappers.toProduct
import domain.mappers.toProductUI
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import presentation.CoroutineViewModel
import presentation.products.ProductUI

class FavoritesViewModel() : CoroutineViewModel(), KoinComponent {
    private val repository by inject<ShopRepository>()

    private val viewModelScope = coroutineScope

    private val _state = MutableStateFlow(FavoritesState())
    val state = _state.asStateFlow()

    init {
        getFavorites()
    }

    private fun getFavorites() {
        viewModelScope.launch {
            repository.getFavorites().collect { favorites ->
                _state.update {
                    favorites.map {
                        it.toProduct().toProductUI(CURRENT_DISCOUNT)
                    }.let { result ->
                        FavoritesState(result)
                    }
                }
            }
        }
    }

    fun saveFavorite(product: Product) {
        viewModelScope.launch {
            repository.saveFavorite(product)
        }
    }

    fun saveFavorite(product: ProductUI) {
        viewModelScope.launch {
            repository.saveFavorite(product.toProduct())
        }
    }

    fun removeFavorite(product: ProductUI) {
        viewModelScope.launch {
            repository.removeFavorite(product.toProduct())
        }
    }


}
