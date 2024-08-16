package br.gohan.shopsample.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import br.gohan.shopsample.ui.Dimens
import coil3.compose.AsyncImage
import presentation.products.ProductUI

@Composable
fun ProductComponent(product: ProductUI, isFavorited: Boolean = false, onClick: (ProductAction) -> Unit) {
    var favorited by remember {
        mutableStateOf(isFavorited)
    }

    Box(
        modifier = Modifier
            .wrapContentHeight()
    ) {
        Card(
            onClick = { onClick(ProductAction.Navigate(product)) },
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(3.dp)
                .width(200.dp),
            shape = RoundedCornerShape(Dimens.cornerSmall)
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
                    style = TextStyle(
                        fontFamily = MaterialTheme.typography.titleMedium.fontFamily,
                    ),
                    fontSize = Dimens.fontSmall,
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .align(Alignment.Start),
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = product.category,
                    style = TextStyle(
                        fontFamily = MaterialTheme.typography.bodySmall.fontFamily,
                    ),
                    color = Color.Gray,
                    modifier = Modifier.padding(top = 6.dp)
                )
                Row(
                    modifier = Modifier
                        .padding(top = 12.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        text = product.newPrice,
                        style = TextStyle(
                            fontFamily = MaterialTheme.typography.bodyMedium.fontFamily,
                        ),
                        fontWeight = FontWeight.Bold,
                    )
                    Text(
                        textDecoration = TextDecoration.LineThrough,
                        text = product.oldPrice,
                        style = TextStyle(
                            fontFamily = MaterialTheme.typography.bodyMedium.fontFamily,
                        ),
                        color = Color.Gray,
                    )
                }
                Row(horizontalArrangement = Arrangement.End) {
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        modifier = Modifier.padding(top = 6.dp),
                        text = product.discount,
                        style = TextStyle(
                            fontFamily = MaterialTheme.typography.labelMedium.fontFamily,
                        ),
                        color = Color(0xFF098109),
                    )
                }
            }
        }

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(40.dp)
                .clickable {
                    favorited = !favorited
                    val message = if (favorited) "Product added to favorites" else "Product removed from favorites"
                    val result = if (favorited) ProductAction.Favorite(product, message) else ProductAction.RemoveFavorite(
                        product, message
                    )
                    onClick(result)
                }
                .align(Alignment.TopEnd).padding(10.dp)
        ) {
            Icon(
                modifier = Modifier.fillMaxSize().sizeIn(35.dp),
                imageVector = if (favorited) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                contentDescription = "Favorite",
            )
        }
    }
}

sealed class ProductAction {
    data class Favorite(val product: ProductUI, val message: String) : ProductAction()
    data class RemoveFavorite(val product: ProductUI, val message: String) : ProductAction()
    data class Navigate(val product: ProductUI) : ProductAction()
    data class AddToCart(val product: ProductUI) : ProductAction()
}

