package presentation

import data.ShopRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import presentation.model.CategoriesState

class CategoriesViewModel(
    private val repository: ShopRepository,
    private val scope: CoroutineScope
) {

    private val _state = MutableStateFlow(CategoriesState())
    val state = _state.asStateFlow()

    init {
        getCategories()
    }

    private fun getCategories() {
        scope.launch {
            _state.update {
                CategoriesState(repository.getCategories())
            }
        }
    }
}