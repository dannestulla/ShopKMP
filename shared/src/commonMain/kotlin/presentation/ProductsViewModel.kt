package presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.ShopRepository
import domain.CURRENT_DISCOUNT
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import presentation.model.ProductUI
import presentation.model.ProductsState


class ProductsViewModel(
    private val category: String? = null,
    private val repository: ShopRepository
) : ViewModel() {

    private val _state = MutableStateFlow(ProductsState())
    val state = _state.asStateFlow()

    init {
        getItems()
    }

    private fun getItems() {
        if (category == null) return
        viewModelScope.launch {
            repository.getProducts()
                .filter { it.category == category }
                .let { products ->
                _state.update {
                    ProductsState(products)
                }
            }
        }
    }

    fun saveFavorite(product: ProductUI) {
        repository.saveFavorite(product, CURRENT_DISCOUNT)
    }

    fun removeFavorite(product: ProductUI) {
        repository.removeFavorite(product)
    }

    fun addToCheckout(product: ProductUI) {
        repository.addToCheckout(product)
    }

    fun checkIfIsFavorite(title: String): Boolean {
        return repository.getFavoriteByTitle(title) != null
    }
}