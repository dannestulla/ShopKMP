package presentation.categories

import data.ShopRepository
import data.model.Product
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import presentation.CoroutineViewModel

class SharedCategoriesViewModel(
) : CoroutineViewModel(), KoinComponent {
    private val repository by inject<ShopRepository>()

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