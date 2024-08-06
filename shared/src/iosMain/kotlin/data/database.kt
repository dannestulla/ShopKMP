package data

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import br.gohan.shopsample.database.FavoritesDatabase
import org.koin.dsl.module


actual val database = module {
    single<SqlDriver> { NativeSqliteDriver(FavoritesDatabase.Schema, "favorites.db") }
}
