package br.gohan.shopsample.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview
import presentation.model.ProductUI

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProductComponent(product: ProductUI, isFavorited: Boolean = false, onClick: (ProductAction) -> Unit) {
    var favorited by remember {
        mutableStateOf(isFavorited)
    }
    val snackbar = remember { SnackbarHostState() }
    val coroutine = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .wrapContentHeight()
            .width(200.dp)
    ) {
        Card(
            onClick = { onClick(ProductAction.Navigate(product)) },
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(3.dp)
                .width(200.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .align(Alignment.CenterHorizontally),
                    model = product.images.first(),
                    contentDescription = product.title
                )
                Text(
                    text = product.title,
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .align(Alignment.Start),
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = product.category,
                    fontSize = 10.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(top = 6.dp)
                )
                Row(
                    modifier = Modifier
                        .padding(top = 12.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = product.newPrice,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                    )
                    Text(
                        textDecoration = TextDecoration.LineThrough,
                        text = product.oldPrice,
                        fontSize = 12.sp,
                        color = Color.Gray,
                    )
                }
                Text(
                    modifier = Modifier.padding(top = 6.dp),
                    text = product.discount,
                    fontSize = 8.sp,
                    color = Color(0xFF098109),
                )
            }
        }

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(30.dp)
                .clickable {
                    favorited = !favorited
                    val message = if (favorited) "Product added to favorites" else "Product removed from favorites"
                    val result = if (favorited) ProductAction.Favorite(product) else ProductAction.RemoveFavorite(
                        product
                    )
                    coroutine.launch {
                        snackbar.showSnackbar(message)
                    }
                    onClick(result)
                }
                .align(Alignment.TopEnd)
        ) {
            Icon(
                imageVector = if (favorited) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                contentDescription = "Favorite",
            )
        }
    }
}

sealed class ProductAction {
    data class Favorite(val product: ProductUI) : ProductAction()
    data class RemoveFavorite(val product: ProductUI) : ProductAction()
    data class Navigate(val product: ProductUI) : ProductAction()
}

@Preview()
@Composable
private fun ProductComponentPreview() {
    ProductComponent(
        ProductUI(
        title = "Shoes",
        oldPrice = "R$ 100,00",
        newPrice = "R$ 200,00",
        discount = "20% OFF",
        description = "",
        images = listOf(""),
        category = "Shoes"
    )
    ) {}
}

