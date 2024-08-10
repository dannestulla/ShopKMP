package br.gohan.shopsample.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import br.gohan.shopsample.ui.Dimens
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Column(
        modifier= modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        CircularProgressIndicator(color = Color.Black, strokeWidth = 3.dp, modifier = Modifier.width(40.dp))
        Spacer(modifier = Modifier.padding(top = Dimens.paddingHuge))
        Text("Loading...", fontSize = Dimens.fontSmall)
    }
}

@Preview
@Composable
private fun LoadingScreenPreview() {
    LoadingScreen()
}