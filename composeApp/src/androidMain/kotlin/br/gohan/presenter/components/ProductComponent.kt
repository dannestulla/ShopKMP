package br.gohan.presenter.components

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
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.gohan.presenter.screens.ProductScreen
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import presentation.ProductUI

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProductComponent(product: ProductUI, onClick: (ProductUI) -> Unit) {
    val favorited by remember {
        mutableStateOf(false)
    }
    Box(
        modifier = Modifier
            .wrapContentHeight()
            .width(200.dp)
    ) {
        Card(
            onClick = { onClick(product) },
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
                    text = "20% OFF",
                    fontSize = 8.sp,
                    color = Color(0xFF098109),
                )
            }
        }
        if (favorited) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(40.dp)
                    .align(Alignment.TopEnd) // Alinha o Box no canto superior direito
            ) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Favorite",
                )
            }
        }
    }
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

