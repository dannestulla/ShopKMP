import br.gohan.shopsample.database.FavoritesDatabase
import org.koin.dsl.module

val iosShared = module {
    single { FavoritesDatabase(
        DatabaseDriverFactory().create()
    ) }
}