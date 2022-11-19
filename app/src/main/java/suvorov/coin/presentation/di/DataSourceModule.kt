package suvorov.coin.presentation.di

import org.koin.dsl.module
import suvorov.coin.data.repository.history.HistoryLocalDataSource
import suvorov.coin.data.repository.history.HistoryRemoteDataSource
import suvorov.coin.data.repository.list.ListLocalDataSource
import suvorov.coin.data.repository.list.ListRemoteDataSource

val dataSourceModule = module {
    single { ListLocalDataSource(get()) }
    single { ListRemoteDataSource(get()) }
    single { HistoryLocalDataSource(get()) }
    single { HistoryRemoteDataSource(get()) }
}