package br.gohan.shopsample.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import br.gohan.shopsample.components.CategoryComponent
import org.koin.compose.koinInject
import presentation.CategoriesViewModel

@Composable
fun CategoriesScreen(
    currentSearch: String?,
    paddingValues: PaddingValues,
    categoriesViewModel: CategoriesViewModel = koinInject(),
    onClick: (String) -> Unit
) {
    val state by categoriesViewModel.state.collectAsState()
    val categories = state.categories

    if (categories == null) {
        LoadingScreen()
        return
    }
    LazyVerticalGrid(
        modifier = Modifier.padding(paddingValues),
        columns = GridCells.Fixed(2)) {
        val filterItems = currentSearch?.let {
            categories.filter {
                it.name.contains(currentSearch, ignoreCase = true)
            }
        } ?: categories
        items(filterItems.size) { index ->
            CategoryComponent(filterItems[index]) { category ->
                onClick(category)
            }
        }
    }
}