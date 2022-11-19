package suvorov.coin.presentation.di

import org.koin.dsl.module
import suvorov.coin.domain.interactor.HistoryInteractor
import suvorov.coin.domain.interactor.HistoryInteractorImpl
import suvorov.coin.domain.interactor.ListInteractor
import suvorov.coin.domain.interactor.ListInteractorImpl

val interactorModule = module {
    factory<ListInteractor> {
        ListInteractorImpl(get())
    }

    factory<HistoryInteractor> {
        HistoryInteractorImpl(get())
    }
}