import br.gohan.shopsample.database.ShopSampleDatabase
import data.ShopRepository
import data.database
import data.local.LocalDataSource
import data.remote.RemoteDataSource
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
import presentation.CategoriesViewModel
import presentation.CheckoutViewModel
import presentation.FavoritesViewModel
import presentation.ProductsViewModel

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
    single { CategoriesViewModel(get()) }
    single { FavoritesViewModel(get()) }
    single { CheckoutViewModel(get()) }
    single { (categories: String) -> ProductsViewModel(categories, get()) }

    factory { ShopRepository(get(), get()) }
    factory { RemoteDataSource(get()) }
    factory { LocalDataSource(get()) }

    single {
        ShopSampleDatabase(
            get(),
        )
    }
}
