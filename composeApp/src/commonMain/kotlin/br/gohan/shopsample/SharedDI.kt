package br.gohan.shopsample

import data.ShopRepository
import data.ShopRepositoryImpl
import data.local.LocalDataSource
import data.remote.RemoteDataSource
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module
import presentation.CategoriesViewModel
import presentation.CheckoutViewModel
import presentation.FavoritesViewModel
import presentation.ProductsViewModel

fun initKoin(appDeclaration: KoinAppDeclaration? = null) = startKoin {
    if (appDeclaration != null) {
        appDeclaration()
    }
    modules(
        api,
        database,
        core
    )
}

expect val api: Module

expect val database: Module

val core = module {
    viewModel { CategoriesViewModel(get()) }
    viewModel { FavoritesViewModel(get()) }
    viewModel { CheckoutViewModel(get()) }
    viewModel { (categories: String?) ->
        ProductsViewModel(categories, get())
    }
    single<ShopRepository> { ShopRepositoryImpl(get(), get()) }

    factory { ShopRepositoryImpl(get(), get()) }
    factory { RemoteDataSource(get()) }
    factory { LocalDataSource(get()) }
}
