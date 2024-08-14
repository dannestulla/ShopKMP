package br.gohan.shopsample.screens

import DropdownMenuComponent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import br.gohan.shopsample.ShopParameters
import br.gohan.shopsample.ui.AppColor
import br.gohan.shopsample.ui.Dimens
import coil3.compose.AsyncImage
import kotlinx.coroutines.launch
import presentation.products.ProductUI

@Composable
fun ProductScreen(shopParameters: ShopParameters) = with(shopParameters) {
    var product by remember { mutableStateOf<ProductUI?>(null) }
    coroutine.launch {
        shopParameters.dataStoreManager.retrieveProduct()?.also {
            favoritesViewModel.checkIfIsFavorite(it.title).also { isFavorite ->
                product = it.copy(isFavorite = isFavorite)
            }
        }
    }
    product?.let {
        ProductScreenStateless(it, paddingValues) { product ->
            checkoutViewModel.addToCart(product)
            coroutine.launch {
                shopParameters.snackbar.showSnackbar(
                    "Product added to cart"
                )
            }
        }
    } ?: run {
        LoadingScreen()
    }
}

@Composable
fun ProductScreenStateless(
    product: ProductUI,
    paddingValues: PaddingValues,
    addToCart: (ProductUI) -> Unit
) {
    val imageNumberSelected = remember { mutableIntStateOf(0) }
    val scrollState = rememberScrollState()
    var sizeSelected by remember { mutableStateOf("37") }

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
            .padding(paddingValues)
            .verticalScroll(scrollState)
            .fillMaxSize()
    ) {
        Spacer(modifier = Modifier.padding(top = Dimens.paddingMedium))
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
                    contentDescription = product.title + "image")
            }
        }
        Spacer(modifier = Modifier.padding(top = Dimens.paddingLarge))
        Text(
            text = product.category, fontSize = Dimens.fontSmall,
            style = TextStyle(
                fontFamily = MaterialTheme.typography.headlineSmall.fontFamily,
            ),
        )
        Spacer(modifier = Modifier.padding(top = Dimens.paddingSmall))
        Text(
            text = product.title, fontSize = Dimens.fontLarge, style = TextStyle(
                fontFamily = MaterialTheme.typography.headlineLarge.fontFamily,
            ), fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.padding(top = Dimens.paddingLarge))
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                text = product.oldPrice,
                textDecoration = TextDecoration.LineThrough,
                fontSize = Dimens.fontSmall,
                color = Color.Gray
            )
            Text(
                text = product.newPrice, fontSize = Dimens.fontSmall,
                style = TextStyle(
                    fontFamily = MaterialTheme.typography.bodyMedium.fontFamily,
                ),
            )
            Spacer(modifier = Modifier.padding(top = Dimens.paddingSmall))
            Text(
                text = product.discount, fontSize = Dimens.fontSmaller, color = AppColor.green,
                style = TextStyle(
                    fontFamily = MaterialTheme.typography.bodyMedium.fontFamily,
                ),
            )
        }
        Spacer(modifier = Modifier.padding(top = Dimens.paddingExtraLarge))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier.padding(top = 3.dp),
                text = "Size:", fontSize = Dimens.fontMedium,
                style = TextStyle(
                    fontFamily = MaterialTheme.typography.bodyLarge.fontFamily,
                ),
            )
            Spacer(modifier = Modifier.padding(horizontal = 20.dp))
            val sizes = listOf("37", "38", "39", "40", "41", "42", "43", "44")
            DropdownMenuComponent(sizes) {
                sizeSelected = it
            }
        }
        Spacer(modifier = Modifier.padding(top = Dimens.paddingLarge))
        Text(
            product.description,
            style = TextStyle(
                fontFamily = MaterialTheme.typography.bodySmall.fontFamily,
            ),
        )
        Spacer(modifier = Modifier.padding(top = Dimens.paddingHuge))
        Button(colors = ButtonColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.onSecondary,
            disabledContainerColor = MaterialTheme.colorScheme.surface,
            disabledContentColor = MaterialTheme.colorScheme.onSurface,
        ),
            modifier = Modifier
                .height(50.dp)
                .fillMaxWidth()
                .padding(horizontal = Dimens.paddingSmall), onClick = {
                product.copy(sizeSelected = sizeSelected).also {
                    addToCart(it)
                }

            }) {
            Text(
                text = "Add to cart", fontSize = Dimens.fontSmall, color = Color.White,
                style = TextStyle(
                    fontFamily = MaterialTheme.typography.labelLarge.fontFamily,
                ),
            )
        }
        Spacer(modifier = Modifier.padding(top = Dimens.paddingExtraHuge))

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

