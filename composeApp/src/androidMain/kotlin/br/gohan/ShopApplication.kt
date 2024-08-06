package br.gohan

import android.app.Application
import initKoin
import org.koin.android.ext.koin.androidContext

class ShopApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@ShopApplication)
        }
    }
}