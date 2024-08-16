package presentation.products

import data.ShopRepository
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
import presentation.favorites.FavoritesState


class ProductsViewModel(private val category: String? = null) : CoroutineViewModel(),
    KoinComponent {
    private val repository by inject<ShopRepository>()

    private val viewModelScope = coroutineScope

    private val _state = MutableStateFlow(FavoritesState())
    val state = _state.asStateFlow()

    init {
        getItems()
    }

    private fun getItems() {
        viewModelScope.launch {
            repository.getProducts().map {
                it.toProductUI(CURRENT_DISCOUNT)
            }.filter { it.category == category }.let { products ->
                _state.update {
                    FavoritesState(products)
                }
            }
        }
    }

    fun saveFavorite(product: ProductUI) {
        repository.saveFavorite(product.toProduct())
    }

    fun removeFavorite(product: ProductUI) {
        repository.removeFavorite(product.toProduct())
    }

    fun addToCheckout(product: ProductUI) {
        repository.addToCheckout(product)
    }

    fun checkIfIsFavorite(title: String): Boolean {
        return repository.getFavoriteByTitle(title) != null
    }
}