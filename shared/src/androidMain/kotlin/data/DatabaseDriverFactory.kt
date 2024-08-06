package data

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import br.gohan.shopsample.database.FavoritesDatabase
import org.koin.dsl.module


actual val database = module {
    single<SqlDriver> {
        AndroidSqliteDriver(
            FavoritesDatabase.Schema,
            get(),
            "favorites.db"
        )
    }
}
