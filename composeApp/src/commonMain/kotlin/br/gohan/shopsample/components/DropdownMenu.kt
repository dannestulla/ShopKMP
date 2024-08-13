import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.gohan.shopsample.ui.Dimens

@Composable
fun DropdownMenuComponent(sizes: List<String>, sizeSelected: (String) -> Unit) {
    val expanded = remember { mutableStateOf(false) }
    val selectedOptionText = remember { mutableStateOf(sizes[0]) }

    Box(
        contentAlignment = Alignment.CenterStart,
        modifier = Modifier
            .size(60.dp, 30.dp)
            .clip(RoundedCornerShape(2.dp))
            .clickable { expanded.value = !expanded.value },
    ) {
        Text(
            modifier = Modifier.padding(start = 4.dp),
            text = selectedOptionText.value,
            fontSize = Dimens.fontSmall,
        )
        Icon(
            Icons.Filled.ArrowDropDown, "contentDescription",
            Modifier.align(Alignment.CenterEnd)
        )
        DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false }
        ) {
            sizes.forEach { selectionOption ->
                DropdownMenuItem(
                    { Text(selectionOption) },
                    onClick = {
                        selectedOptionText.value = selectionOption
                        expanded.value = false
                    },
                )

            }
        }
    }
}
