package suvorov.coin.presentation.di

import org.koin.dsl.module
import suvorov.coin.data.repository.history.HistoryRepositoryImpl
import suvorov.coin.data.repository.list.ListRepositoryImpl
import suvorov.coin.domain.repository.HistoryRepository
import suvorov.coin.domain.repository.ListRepository

val repositoryModule = module {
    factory<ListRepository> {
        ListRepositoryImpl(get(), get(), get())
    }

    factory<HistoryRepository> {
        HistoryRepositoryImpl(get(), get())
    }
}