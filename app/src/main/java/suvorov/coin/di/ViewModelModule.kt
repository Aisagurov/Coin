package suvorov.coin.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import suvorov.coin.ui.base.SharedViewModel
import suvorov.coin.ui.categories.CategoriesViewModel
import suvorov.coin.ui.category.CategoryViewModel
import suvorov.coin.ui.currencies.CurrenciesViewModel
import suvorov.coin.ui.currency.CurrencyViewModel
import suvorov.coin.ui.derivative.DerivativeViewModel
import suvorov.coin.ui.derivatives.DerivativesViewModel
import suvorov.coin.ui.exchange.ExchangeViewModel
import suvorov.coin.ui.exchanges.ExchangesViewModel
import suvorov.coin.ui.portfolio.PortfolioViewModel
import suvorov.coin.ui.login.LoginViewModel

val viewModelModule = module {
    viewModel {
        SharedViewModel()
    }
    viewModel {
        CurrenciesViewModel(get(), get())
    }
    viewModel {
        CategoriesViewModel(get())
    }
    viewModel {
        ExchangesViewModel(get())
    }
    viewModel {
        DerivativesViewModel(get())
    }
    viewModel {
        PortfolioViewModel(get())
    }
    viewModel {
        CurrencyViewModel(get())
    }
    viewModel {
        CategoryViewModel(get())
    }
    viewModel {
        ExchangeViewModel(get())
    }
    viewModel {
        DerivativeViewModel(get())
    }
    viewModel {
        LoginViewModel(get())
    }
}