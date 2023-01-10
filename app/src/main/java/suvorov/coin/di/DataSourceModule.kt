package suvorov.coin.di

import org.koin.dsl.module
import suvorov.coin.data.repository.categories.CategoriesLocalDataSource
import suvorov.coin.data.repository.categories.CategoriesRemoteDataSource
import suvorov.coin.data.repository.category.CategoryLocalDataSource
import suvorov.coin.data.repository.currencies.CurrenciesLocalDataSource
import suvorov.coin.data.repository.currencies.CurrenciesRemoteDataSource
import suvorov.coin.data.repository.currency.CurrencyLocalDataSource
import suvorov.coin.data.repository.currency.CurrencyRemoteDataSource
import suvorov.coin.data.repository.derivative.DerivativeLocalDataSource
import suvorov.coin.data.repository.derivatives.DerivativesLocalDataSource
import suvorov.coin.data.repository.derivatives.DerivativesRemoteDataSource
import suvorov.coin.data.repository.exchange.ExchangeLocalDataSource
import suvorov.coin.data.repository.exchange.ExchangeRemoteDataSource
import suvorov.coin.data.repository.exchanges.ExchangesLocalDataSource
import suvorov.coin.data.repository.exchanges.ExchangesRemoteDataSource
import suvorov.coin.data.repository.global.GlobalLocalDataSource
import suvorov.coin.data.repository.global.GlobalRemoteDataSource
import suvorov.coin.data.repository.portfolio.PortfolioLocalDataSource

val dataSourceModule = module {
    factory { GlobalRemoteDataSource(get()) }
    factory { GlobalLocalDataSource(get()) }

    factory { CurrenciesRemoteDataSource(get()) }
    factory { CurrenciesLocalDataSource(get()) }

    factory { CategoriesRemoteDataSource(get()) }
    factory { CategoriesLocalDataSource(get()) }

    factory { ExchangesRemoteDataSource(get()) }
    factory { ExchangesLocalDataSource(get()) }

    factory { DerivativesRemoteDataSource(get()) }
    factory { DerivativesLocalDataSource(get()) }

    factory { PortfolioLocalDataSource(get()) }

    factory { CurrencyRemoteDataSource(get()) }
    factory { CurrencyLocalDataSource(get()) }

    factory { CategoryLocalDataSource(get()) }

    factory { ExchangeRemoteDataSource(get()) }
    factory { ExchangeLocalDataSource(get()) }

    factory { DerivativeLocalDataSource(get()) }
}