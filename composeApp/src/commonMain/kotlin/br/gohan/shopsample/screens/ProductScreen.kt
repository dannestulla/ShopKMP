package br.gohan.shopsample.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import br.gohan.presenter.ui.AppColor
import br.gohan.presenter.ui.Dimens
import br.gohan.shopsample.ui.ShopTheme
import coil3.compose.AsyncImage
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import presentation.model.ProductUI
import shopsample.composeapp.generated.resources.Res
import shopsample.composeapp.generated.resources.orange_svgrepo_com

@Composable
fun ProductScreen(product: ProductUI, addToCart: () -> Unit) {
    val imageNumberSelected = remember { mutableIntStateOf(0) }
    val scrollState = rememberScrollState()
    val dropdownExpanded by remember { mutableStateOf(false) }
    var sizeSelected by remember { mutableStateOf("40") }

    val imageSelected by remember {
        derivedStateOf {
            product.images[imageNumberSelected.intValue]
        }
    }
    val progress by remember {
        derivedStateOf {
            calculateProgress(
                currentIndex = imageNumberSelected.intValue,
                totalItems = product.images.size
            )
        }
    }
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(durationMillis = 200), label = ""
    )

    Column(
        modifier = Modifier
            .padding(Dimens.paddingLarge)
            .verticalScroll(scrollState)
            .fillMaxSize()
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp),
            model = imageSelected,
            contentDescription = product.title + "image",
        )
        Spacer(modifier = Modifier.padding(top = Dimens.paddingExtraHuge))
        LinearProgressIndicator(
            modifier = Modifier
                .padding(horizontal = Dimens.paddingExtraHuge)
                .fillMaxWidth(),
            progress = animatedProgress, color = Black, backgroundColor = Color.LightGray
        )
        Spacer(modifier = Modifier.padding(top = Dimens.paddingExtraHuge))
        LazyRow(horizontalArrangement = Arrangement.spacedBy(Dimens.paddingMedium)) {
            items(product.images.size) {
                AsyncImage(
                    modifier = Modifier
                        .clickable {
                            imageNumberSelected.intValue = it
                        }
                        .height(100.dp),
                    model = product.images[it],
                    placeholder = painterResource(Res.drawable.orange_svgrepo_com),
                    contentDescription = product.title + "image")
            }
        }
        Spacer(modifier = Modifier.padding(top = Dimens.paddingExtraHuge))
        Text(text = product.category, fontSize = Dimens.fontSmall, fontWeight = FontWeight.Bold)
        Text(text = product.title, fontSize = Dimens.fontLarge, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.padding(top = Dimens.paddingLarge))
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            Text(
                text = product.oldPrice,
                textDecoration = TextDecoration.LineThrough,
                fontSize = Dimens.fontSmall,
                color = Color.Gray
            )
            Text(text = product.newPrice, fontSize = Dimens.fontSmall)
            Spacer(modifier = Modifier.padding(top = Dimens.paddingSmall))
            Text(text = product.discount, fontSize = Dimens.fontSmall, color = AppColor.green)
        }
        Spacer(modifier = Modifier.padding(top = Dimens.paddingLarge))
        Text(text = "Sizes", fontSize = Dimens.fontLarge, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.padding(top = Dimens.paddingLarge))
        /*DropdownMenu(expanded = dropdownExpanded, onDismissRequest = { }) {
            DropdownMenuItem(onClick = { sizeSelected = "4" }) {
                Text(text = "teste")
            }
        }*/
        Button(
            shape = MaterialTheme.shapes.large,
            modifier = Modifier
            .height(50.dp)
            .fillMaxWidth()
            .padding(horizontal = Dimens.paddingSmall), onClick = { addToCart() }) {
            Text(text = "Add to cart")
        }
    }
}


internal fun calculateProgress(currentIndex: Int, totalItems: Int): Float {
    return if (currentIndex == 0) {
        0.2f
    } else if (totalItems > 0) {
        (currentIndex + 0.5f) / totalItems.toFloat()
    } else {
        0f
    }
}

@Preview()
@Composable
private fun ProductScreenPreview() {
    ShopTheme {
        ProductScreen(
            ProductUI(
                title = "Shoes",
                oldPrice = "R$ 100,00",
                newPrice = "R$ 200,00",
                discount = "20% OFF",
                description = "",
                category = "Shoes",
                images = listOf(
                    "assd",
                    "asdsad",
                    "asdsad",
                    "asdsad",
                    "asdsad",
                    "asdsad",
                )
            )
        , {})
    }
}
