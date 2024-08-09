package br.gohan.shopkmp

import androidx.compose.ui.window.ComposeUIViewController
import br.gohan.presenter.ShopApp

fun MainViewController() = ComposeUIViewController { ShopApp() }