package br.gohan.shopsample.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import br.gohan.shopsample.components.CheckoutItem
import presentation.checkout.CheckoutUI
import presentation.checkout.CheckoutViewModel

@Composable
fun CheckoutScreen(
    paddingValues: PaddingValues,
    checkoutViewModel: CheckoutViewModel,
    action: () -> Unit
) {
    LaunchedEffect(Unit) {
        checkoutViewModel.getItems()
    }

    val checkoutItems = checkoutViewModel.state.collectAsState()

    if (checkoutItems.value.checkoutItems == null) {
        LoadingScreen()
    } else {
        CheckoutScreenStateless(paddingValues, checkoutItems.value.checkoutItems!!)
    }
}

@Composable
fun CheckoutScreenStateless(
    paddingValues: PaddingValues,
    checkoutItems: List<CheckoutUI>
) {
    var items by remember { mutableStateOf(checkoutItems) }
    var confirmItemDeletion by remember { mutableStateOf<CheckoutUI?>(null) }

    if (confirmItemDeletion != null) {
        AlertDialog(
            onDismissRequest = { },
            title = { Text(text = "Do you want to remove this item from the cart?") },
            //text = { Text(text = "Conteúdo do dialog") },
            confirmButton = {
                TextButton(onClick = {
                    items = items.filter { it != confirmItemDeletion }
                }) {
                    Text("Delete")
                }
            },
            dismissButton = {
                TextButton(onClick = { confirmItemDeletion = null }) {
                    Text("Cancel")
                }
            }
        )
    }

    Column(
        modifier = Modifier.padding(paddingValues).fillMaxSize(),
    ) {
        if (items.isEmpty()) {
            Text("No products in the cart")
        } else {
            LazyColumn {
                items(items.size) { index ->
                    CheckoutItem(items[index]) { productToDelete ->
                        confirmItemDeletion = productToDelete

                    }
                }
            }
        }
    }
}
