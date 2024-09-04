package br.gohan.shopsample

import androidx.compose.ui.window.ComposeUIViewController

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) {
    ShopApp(NativeDataStoreManager())
}
