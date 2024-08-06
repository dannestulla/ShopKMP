package br.gohan.presenter.screens

import android.widget.ProgressBar
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.gohan.R
import br.gohan.presenter.components.Placeholder
import br.gohan.presenter.components.SizeGrid
import br.gohan.presenter.ui.Dimens
import coil.compose.AsyncImage
import presentation.ProductUI

@Composable
fun ProductScreen(product: ProductUI) {
    val selectedSize = remember { mutableStateOf("40") }
    val scrollState = rememberScrollState()
    val imageSelected = remember { mutableIntStateOf(0) }
    val progress by remember { derivedStateOf {
        calculateProgress(
            currentIndex = imageSelected.intValue,
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
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,

    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp),
            model = product.images.first(),
            contentDescription = product.title + "image",
            placeholder = painterResource(R.drawable.orange_svgrepo_com),
        )
        Spacer(modifier = Modifier.padding(top = Dimens.paddingExtraHuge))
        LinearProgressIndicator(
            modifier = Modifier.padding(horizontal = Dimens.paddingExtraHuge).fillMaxWidth(),
            progress = animatedProgress, color = Black, backgroundColor = Color.LightGray)
        Spacer(modifier = Modifier.padding(top = Dimens.paddingExtraHuge))
        LazyRow {
            items(product.images.size) {
                AsyncImage(
                    modifier = Modifier
                        .padding(vertical = Dimens.paddingMedium)
                        .fillMaxWidth()
                        .clickable {
                            imageSelected.intValue = it
                        }
                        .height(100.dp),
                    model = product.images[it],
                    placeholder = painterResource(R.drawable.orange_svgrepo_com),
                    contentDescription = product.title + "image")
            }
        }
        Spacer(modifier = Modifier.padding(top = Dimens.paddingMedium))
        Text(text = product.category, fontSize = 16.sp)
        Text(text = product.title, fontSize = Dimens.fontSizeMedium)
        Spacer(modifier = Modifier.padding(top = Dimens.paddingSmall))
        Row {
            Text(text = product.oldPrice, textDecoration = TextDecoration.LineThrough, fontSize = Dimens.fontSizeSmall)
            Text(text = product.newPrice, fontSize = Dimens.fontSizeSmall)
            Text(text = product.discount, fontSize = Dimens.fontSizeSmall)
        }
        Text(text = "Sizes")
        SizeGrid(selectedSize)
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

@Preview(showSystemUi = true)
@Composable
private fun ProductScreenPreview() {
    return ProductScreen(
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
    )
}