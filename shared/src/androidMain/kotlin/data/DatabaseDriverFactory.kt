package data

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import br.gohan.shopsample.database.ShopSampleDatabase
import org.koin.dsl.module


actual val database = module {
    single<SqlDriver> {
        AndroidSqliteDriver(
            ShopSampleDatabase.Schema,
            get(),
            "shopsample.db"
        )
    }
}
