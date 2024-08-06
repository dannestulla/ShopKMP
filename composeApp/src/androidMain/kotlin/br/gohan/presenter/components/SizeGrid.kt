package br.gohan.presenter.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SizeGrid(selectedSize: MutableState<String>) {
    val sizes = listOf("37", "37.5", "38", "39", "39.5", "40", "40.5", "41", "42", "42.5", "43", "43.5", "44", "45", "46", "47", "48")

    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize(),
        columns = GridCells.Fixed(4),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(sizes.size) {
            SizeItem(sizes[it], isSelected = sizes[it] == selectedSize.value) {
                selectedSize.value = sizes[it]
            }
        }
    }
}

@Composable
fun SizeItem(size: String, isSelected: Boolean, onClick: () -> Unit) {
    Card(
        shape = RectangleShape,
        modifier = Modifier
            .size(50.dp)
            .clickable { onClick() }
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
                .border(1.dp, Color.Black, RoundedCornerShape(2.dp))

        ) {
            Text(
                text = size,
                textAlign = TextAlign.Center,
                fontSize = 16.sp,
                color = if (isSelected) Color.Black else Color.LightGray
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun SizeGridPreview() {
    val selectedSize = remember { mutableStateOf("40") }
    SizeGrid(selectedSize)
}