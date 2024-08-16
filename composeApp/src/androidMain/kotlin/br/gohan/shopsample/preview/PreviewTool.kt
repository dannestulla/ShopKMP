package br.gohan.shopsample.preview

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import br.gohan.shopsample.screens.ProductScreenStateless
import presentation.model.ProductUI

/**
 * Used to preview composable since preview inside commonMain is not working
 */
@Preview(showBackground = true)
@Composable
fun Preview2() {
    ProductScreenStateless(
        product = ProductUI(
            "Elegant purble Lether Loafers",
            "300",
            "200",
            "30 %",
            "esse é um sapato lalalallala",
            listOf("https://picsum.photos/200/300"),
            category = "shoes",
            false,
            "40"

        ),

        paddingValues = PaddingValues()
    ) {}
}



