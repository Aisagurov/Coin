package suvorov.coin.di

import org.koin.dsl.module
import suvorov.coin.data.repository.categories.CategoriesRepositoryImpl
import suvorov.coin.data.repository.category.CategoryRepositoryImpl
import suvorov.coin.data.repository.currencies.CurrenciesRepositoryImpl
import suvorov.coin.data.repository.currency.CurrencyRepositoryImpl
import suvorov.coin.data.repository.derivative.DerivativeRepositoryImpl
import suvorov.coin.data.repository.derivatives.DerivativesRepositoryImpl
import suvorov.coin.data.repository.exchange.ExchangeRepositoryImpl
import suvorov.coin.data.repository.exchanges.ExchangesRepositoryImpl
import suvorov.coin.data.repository.global.GlobalRepositoryImpl
import suvorov.coin.data.repository.portfolio.PortfolioRepositoryImpl
import suvorov.coin.domain.repository.*

val repositoryModule = module {
    factory<GlobalRepository> {
        GlobalRepositoryImpl(get(), get(), get())
    }
    factory<CurrenciesRepository> {
        CurrenciesRepositoryImpl(get(), get(), get())
    }
    factory<CategoriesRepository> {
        CategoriesRepositoryImpl(get(), get(), get())
    }
    factory<ExchangesRepository> {
        ExchangesRepositoryImpl(get(), get(), get())
    }
    factory<DerivativesRepository> {
        DerivativesRepositoryImpl(get(), get(), get())
    }
    factory<PortfolioRepository> {
        PortfolioRepositoryImpl(get())
    }
    factory<CurrencyRepository> {
        CurrencyRepositoryImpl(get(), get())
    }
    factory<CategoryRepository> {
        CategoryRepositoryImpl(get())
    }
    factory<ExchangeRepository> {
        ExchangeRepositoryImpl(get(), get())
    }
    factory<DerivativeRepository> {
        DerivativeRepositoryImpl(get())
    }
}