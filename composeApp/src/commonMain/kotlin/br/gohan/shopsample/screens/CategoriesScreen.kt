package br.gohan.shopsample.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import br.gohan.shopsample.components.CategoryComponent
import presentation.categories.CategoriesViewModel


@Composable
fun CategoriesScreen(
    currentSearch: String?,
    paddingValues: PaddingValues,
    onClick: (String) -> Unit
) {
    val categoriesViewModel = remember { CategoriesViewModel() }
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
            CategoryComponent(filterItems[index], onClick)
        }
    }
}