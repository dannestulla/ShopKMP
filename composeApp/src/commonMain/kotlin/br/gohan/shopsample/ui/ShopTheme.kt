package br.gohan.shopsample.ui
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import org.jetbrains.compose.resources.Font
import shopsample.composeapp.generated.resources.OpenSans_Bold
import shopsample.composeapp.generated.resources.OpenSans_Medium
import shopsample.composeapp.generated.resources.OpenSans_Regular
import shopsample.composeapp.generated.resources.Res
import shopsample.composeapp.generated.resources.Rubik_Bold
import shopsample.composeapp.generated.resources.Rubik_Regular


@Composable
fun ShopTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val displayFontFamily = FontFamily(
        Font(
            Res.font.Rubik_Regular,
            weight = FontWeight.Normal
        ),
        Font(
            Res.font.Rubik_Regular,
            weight = FontWeight.Medium
        ),
        Font(
            Res.font.Rubik_Bold,
            weight = FontWeight.Bold
        ),
    )

    val bodyFontFamily = FontFamily(
        Font(
            Res.font.OpenSans_Regular,
            weight = FontWeight.Normal
        ),
        Font(
            Res.font.OpenSans_Medium,
            weight = FontWeight.Medium
        ),
        Font(
            Res.font.OpenSans_Bold,
            weight = FontWeight.Bold
        )
    )

    val colorScheme = when {
      darkTheme -> lightScheme
      else -> lightScheme
  }

  MaterialTheme(
    colorScheme = colorScheme,
      typography = getAppTypography(displayFontFamily, bodyFontFamily),
    content = content
  )
}

