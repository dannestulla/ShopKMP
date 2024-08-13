package presentation.favorites

import data.ShopRepository
import data.model.Product
import domain.CURRENT_DISCOUNT
import domain.toProduct
import domain.toProductUI
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import presentation.CoroutineViewModel
import presentation.model.ProductUI

class SharedFavoritesViewModel() : CoroutineViewModel(), KoinComponent {
    private val repository by inject<ShopRepository>()

    private val viewModelScope = coroutineScope

    private val _state = MutableStateFlow(FavoritesState())
    val state = _state.asStateFlow()

    private val _productIsFavorite = MutableStateFlow(false)
    val productIsFavorite = _productIsFavorite.asStateFlow()

    fun getFavorites() {
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
        getFavorites()
    }

    fun checkIfIsFavorite(title: String): Boolean {
        return repository.getFavoriteByTitle(title) != null
    }
}
