package br.gohan.shopsample.components

import DropdownMenuComponent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.gohan.shopsample.ui.Dimens
import coil3.compose.AsyncImage
import domain.currencyToDouble
import domain.toCurrency
import presentation.checkout.CheckoutUI


@Composable
fun CheckoutItem(checkoutItem: CheckoutUI, confirmDelete: (CheckoutUI) -> Unit) {
    val quantity by remember {
        mutableStateOf(listOf(0, 1, 2, 3, 4, 5))
    }
    var quantitySelected by remember {
        mutableStateOf(1)
    }

    Box(
        modifier = Modifier
            .padding(4.dp)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(Dimens.cornerSmall)
        ) {
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    AsyncImage(
                        modifier = Modifier.size(100.dp, 100.dp),
                        model = checkoutItem.image,
                        contentDescription = "Product Image",
                    )
                    Column(
                        modifier = Modifier.padding(start = 14.dp),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = checkoutItem.title,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Row {
                            Text(text = "Size: ", color = Color.DarkGray)
                            Text(text = checkoutItem.sizeSelected, color = Color.DarkGray)
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = checkoutItem.oldPrice,
                                textDecoration = TextDecoration.LineThrough,
                                color = Color.DarkGray
                            )
                            Spacer(modifier = Modifier.width(20.dp))
                            Text(text = checkoutItem.price, fontSize = 16.sp)
                        }
                    }

                }
                Spacer(modifier = Modifier.height(14.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Quantity ", fontSize = Dimens.fontSmall)
                    Spacer(modifier = Modifier.width(14.dp))
                    DropdownMenuComponent(quantity.map { it.toString() }) { selected ->
                        if (selected.toInt() == 0) {
                            quantitySelected = 1
                            confirmDelete(checkoutItem)
                        } else {
                            quantitySelected = selected.toInt()
                        }
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = quantitySelected.times(checkoutItem.price.currencyToDouble())
                            .toCurrency(),
                        fontSize = Dimens.fontSmall,
                        fontWeight = FontWeight.Bold
                    )
                    IconButton(onClick = {
                        confirmDelete(checkoutItem)
                    }) {
                        Icon(Icons.Default.Delete, contentDescription = "Remove Item")
                    }

                }
            }
        }
    }
}


