package br.gohan.shopsample.screens

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import data.model.Categories
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import br.gohan.shopsample.components.CategoryComponent
import br.gohan.shopsample.ui.Dimens
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
fun CategoriesScreen(categories: List<Categories>?, currentSearch: String?, onClick: (String) -> Unit) {
    if (categories == null) {
        LoadingScreen()
        return
    }
    LazyVerticalGrid(
        modifier = Modifier.padding(Dimens.paddingMedium),
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

@Preview()
@Composable
private fun CategoriesScreenPreview() {
    CategoriesScreen(
        listOf(
            Categories(
                name = "Shoes",
                image = "",
            ),
            Categories(
                name = "Shoes",
                image = "",
            ),
            Categories(
                name = "Shoes",
                image = "",
            ),
            Categories(
                name = "Shoes",
                image = "",
            ),
            Categories(
                name = "Shoes",
                image = "",
            ),
        ),
        ""
    ) {}
}