package br.gohan.shopsample


import androidx.datastore.preferences.core.stringPreferencesKey
import presentation.products.ProductUI


val PRODUCT_KEY = stringPreferencesKey("product")


interface DataStoreManager {
    suspend fun cacheProduct(productUI: ProductUI)

    suspend fun retrieveProduct(): ProductUI?
}
