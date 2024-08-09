package br.gohan.shopsample.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.gohan.shopsample.components.ProductAction
import br.gohan.shopsample.components.ProductComponent
import presentation.model.ProductUI

@Composable
fun ProductsScreen(products: List<ProductUI>?, currentSearch: String?, onClick : (ProductAction) -> Unit) {
    if (products == null) {
        LoadingScreen()
        return
    }
    LazyVerticalGrid(
        modifier = Modifier.padding(4.dp),
        columns = GridCells.Fixed(2)) {
        val filteredProducts = currentSearch?.let {products.filter {
            it.title.contains(currentSearch, ignoreCase = true)
        }} ?: products
        items(filteredProducts.size) { index ->
            ProductComponent(filteredProducts[index], false, onClick)
        }
    }
}