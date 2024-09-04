package presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.ShopRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import presentation.model.CategoriesState

class CategoriesViewModel(
    private val repository: ShopRepository
) : ViewModel() {

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