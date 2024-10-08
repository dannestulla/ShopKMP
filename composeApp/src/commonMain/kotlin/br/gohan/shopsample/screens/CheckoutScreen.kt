package br.gohan.shopsample.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import br.gohan.shopsample.components.CheckoutItem
import br.gohan.shopsample.ui.Dimens
import org.koin.compose.koinInject
import presentation.CheckoutViewModel
import presentation.model.CheckoutUI

@Composable
fun CheckoutScreen(
    paddingValues: PaddingValues,
    checkoutViewModel: CheckoutViewModel = koinInject()
) {
    val checkoutItems by checkoutViewModel.state.collectAsState()

    if (checkoutItems.checkoutItems == null) {
        LoadingScreen()
    } else {
        CheckoutScreenStateless(paddingValues, checkoutItems.checkoutItems!!) {
            checkoutViewModel.removeFromCheckout(it)
        }
    }
}

@Composable
fun CheckoutScreenStateless(
    paddingValues: PaddingValues,
    checkoutItems: List<CheckoutUI> = emptyList(),
    deleteItem: (CheckoutUI) -> Unit
) {
    var items by remember { mutableStateOf(checkoutItems) }
    var confirmItemDeletion by remember { mutableStateOf<CheckoutUI?>(null) }

    if (confirmItemDeletion != null) {
        AlertDialog(
            onDismissRequest = { },
            title = {
                Text(
                    text = "Do you want to remove this item from the cart?",
                    fontSize = Dimens.fontLarge
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    val deletedItem = items.firstOrNull { it == confirmItemDeletion }
                    items = items.filter { it != confirmItemDeletion }
                    deletedItem?.let {
                        deleteItem(deletedItem)
                    }
                    confirmItemDeletion = null
                }) {
                    Text("Delete")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    confirmItemDeletion = null

                }) {
                    Text("Cancel")
                }
            }
        )
    }

    Column(
        verticalArrangement = if (checkoutItems.isEmpty()) Arrangement.Center else Arrangement.Top,
        horizontalAlignment = if (checkoutItems.isEmpty()) Alignment.CenterHorizontally else Alignment.Start,
        modifier = Modifier.padding(paddingValues)
            .fillMaxSize()
    ) {
        if (items.isEmpty()) {
            Text(
                "No products in the cart", fontSize = Dimens.fontSmall,
                style = TextStyle(
                    fontFamily = MaterialTheme.typography.headlineMedium.fontFamily,
                ),
            )
        } else {
            LazyColumn(Modifier.wrapContentHeight()) {
                items(items.size) { index ->
                    CheckoutItem(items[index]) { productToDelete ->
                        confirmItemDeletion = productToDelete
                    }
                }
            }
            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}
