package br.gohan.presenter.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter

@Composable
fun Placeholder(modifier: Modifier = Modifier) {
    rememberAsyncImagePainter(
        Brush.linearGradient(
            listOf(
                Color.White,
                Color(0xFFDDDDDD)
            )
        )
    )
}

@Preview(showSystemUi = true)
@Composable
private fun PlaceholderPreview() {
    Placeholder()
}