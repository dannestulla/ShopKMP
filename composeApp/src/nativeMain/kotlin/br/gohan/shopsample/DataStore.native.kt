package br.gohan.shopsample

import kotlinx.serialization.json.Json
import platform.Foundation.NSUserDefaults
import presentation.model.ProductUI


class NativeDataStoreManager : DataStoreManager {
    private val userDefaults = NSUserDefaults.standardUserDefaults

    override suspend fun cacheProduct(productUI: ProductUI) {
        val jsonString = Json.encodeToString(ProductUI.serializer(), productUI)
        userDefaults.setObject(jsonString, forKey = PRODUCT_KEY.name)
    }

    override suspend fun retrieveProduct(): ProductUI? {
        val jsonString = userDefaults.stringForKey(PRODUCT_KEY.name)
        return jsonString?.let { json ->
            Json.decodeFromString(ProductUI.serializer(), json)
        }
    }

}