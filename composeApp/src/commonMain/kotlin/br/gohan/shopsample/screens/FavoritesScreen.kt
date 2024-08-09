package br.gohan.shopsample.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import br.gohan.shopsample.components.ProductAction
import br.gohan.shopsample.components.ProductComponent
import br.gohan.presenter.ui.Dimens
import org.jetbrains.compose.ui.tooling.preview.Preview
import presentation.model.ProductUI

@Composable
fun FavoritesScreen(product: List<ProductUI>? = emptyList(), action: (ProductAction) -> Unit) {
    if (product == null) {
        LoadingScreen()
        return
    }
    if (product.isEmpty()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            Text("No favorites yet")
        }
    }
    LazyVerticalGrid(
        modifier = Modifier.padding(Dimens.paddingMedium),
        columns = GridCells.Fixed(2)) {
        items(product.size) { index ->
            ProductComponent(product[index], true, action)
        }
    }
}

@Preview()
@Composable
private fun FavoritesScreenPreview() {
    FavoritesScreen(mockList) {

    }
}

val mockList = listOf(
    ProductUI(
        title = "Shoes",
        oldPrice = "R$ 100,00",
        newPrice = "R$ 200,00",
        discount = "20% OFF",
        description = "",
        images = listOf(""),
        category = "Shoes"
    ),
)