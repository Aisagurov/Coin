package suvorov.coin.presentation.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import suvorov.coin.presentation.ui.viewmodel.HistoryViewModel
import suvorov.coin.presentation.ui.viewmodel.ListViewModel

val viewModelModule = module {
    viewModel {
        ListViewModel(get())
    }

    viewModel {
        HistoryViewModel(get())
    }
}