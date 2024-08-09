package br.gohan.shopsample


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import br.gohan.presenter.ShopApp


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShopApp()
        }
    }
}
