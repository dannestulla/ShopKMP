import br.gohan.shopsample.database.ShopSampleDatabase
import data.ShopRepository
import data.database
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import org.koin.dsl.module
import presentation.categories.SharedCategoriesViewModel
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import io.ktor.client.plugins.logging.Logging
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import presentation.favorites.SharedFavoritesViewModel
import presentation.items.ProductsViewModel

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
    single { SharedCategoriesViewModel() }
    single { ProductsViewModel() }
    single { SharedFavoritesViewModel() }
    factory { ShopRepository(get(), get()) }

    single {
        ShopSampleDatabase(
            get(),
        )
    }
}
