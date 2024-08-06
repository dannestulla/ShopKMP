package br.gohan.presenter.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.gohan.presenter.components.ProductComponent
import presentation.ProductUI

@Composable
fun ProductsScreen(products: List<ProductUI>, onClick : (ProductUI) -> Unit) {
    LazyVerticalGrid(
        modifier = Modifier.padding(4.dp),
        columns = GridCells.Fixed(2)) {
        items(products.size) { index ->
            ProductComponent(products[index], onClick)
        }
    }
}