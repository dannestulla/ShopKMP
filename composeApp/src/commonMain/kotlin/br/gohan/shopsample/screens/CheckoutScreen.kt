package br.gohan.shopsample.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import br.gohan.shopsample.ui.Dimens
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun CheckoutScreen() {
    Column(
        modifier = Modifier.fillMaxSize().padding(Dimens.paddingMedium),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Checkout Screen")
    }
}

@Preview()
@Composable
private fun CheckoutScreenPreview() {
    CheckoutScreen()
}