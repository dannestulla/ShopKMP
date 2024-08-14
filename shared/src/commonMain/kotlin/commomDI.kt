import br.gohan.shopsample.database.ShopSampleDatabase
import data.ShopRepository
import data.database
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module
import presentation.categories.CategoriesViewModel
import presentation.favorites.FavoritesViewModel
import presentation.products.ProductsViewModel

fun initKoin(appDeclaration: KoinAppDeclaration) = startKoin {
    appDeclaration()
    modules(
        api,
        database,
        core
    )
}

val api = module {
    single {
        HttpClient(CIO) {
            install(Logging) {
                level = LogLevel.ALL
            }
            install(ContentNegotiation) {
                json(
                    json = Json {
                        ignoreUnknownKeys = true
                    }
                )
            }
        }
    }
}

val core = module {
    single { CategoriesViewModel() }
    single { ProductsViewModel() }
    single { FavoritesViewModel() }
    factory { ShopRepository(get(), get()) }

    single {
        ShopSampleDatabase(
            get(),
        )
    }
}
