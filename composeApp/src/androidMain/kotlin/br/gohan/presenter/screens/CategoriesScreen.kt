package br.gohan.presenter.screens

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import data.model.Categories
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import br.gohan.presenter.components.CategoryComponent
import br.gohan.presenter.ui.Dimens


@Composable
fun CategoriesScreen(categories: List<Categories>, onClick: (String) -> Unit) {
    LazyVerticalGrid(
        modifier = Modifier.padding(Dimens.paddingMedium),
        columns = GridCells.Fixed(2)) {
        items(categories.size) { index ->
            CategoryComponent(categories[index], onClick)
        }
    }
}

@Preview(showSystemUi = true)
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
    ) {}
}