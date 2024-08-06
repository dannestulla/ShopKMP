package br.gohan.presenter.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import br.gohan.presenter.ui.Dimens

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

@Preview(showSystemUi = true)
@Composable
private fun CheckoutScreenPreview() {
    CheckoutScreen()
}