package br.gohan.shopsample

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import br.gohan.shopsample.database.ShopSampleDatabase
import org.koin.dsl.module


actual val database = module {
    single<SqlDriver> {
        NativeSqliteDriver(ShopSampleDatabase.Schema, "shopsample.db")
    }

    single {
        ShopSampleDatabase(
            get(),
        )
    }
}
