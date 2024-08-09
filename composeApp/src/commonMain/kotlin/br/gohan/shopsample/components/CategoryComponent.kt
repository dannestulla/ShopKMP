package br.gohan.shopsample.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.gohan.presenter.ui.Dimens
import coil3.compose.AsyncImage
import data.model.Categories
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CategoryComponent(categories: Categories, onClick: (String) -> Unit) {
    Box(
        modifier = Modifier
            .wrapContentHeight()
            .width(200.dp)
    ) {
        Card(
            onClick = {
                onClick.invoke(categories.name)
            },
            modifier = Modifier
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
                    model = categories.image,
                    contentDescription = categories.name
                )
                Text(
                    text = categories.name,
                    modifier = Modifier
                        .padding(top = Dimens.paddingSmall, start = Dimens.paddingMedium)
                        .align(Alignment.Start),
                )
            }
        }
    }
}

@Preview
@Composable
private fun CategoryComponentPreview() {
    CategoryComponent(categories = Categories("Shoes", "")) {

    }
}

