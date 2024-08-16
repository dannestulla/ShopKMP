package presentation

import data.ShopRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import presentation.model.CategoriesState

class CategoriesViewModel(
    private val repository: ShopRepository
) : CoroutineViewModel(), KoinComponent {

    private val viewModelScope = coroutineScope

    private val _state = MutableStateFlow(CategoriesState())
    val state = _state.asStateFlow()

    init {
        getCategories()
    }

    private fun getCategories() {
        viewModelScope.launch {
            _state.update {
                CategoriesState(repository.getCategories())
            }
        }
    }
}