package suvorov.coin.di

import org.koin.dsl.module
import suvorov.coin.data.local.room.CoinDatabase

val databaseModule = module {
    single { CoinDatabase.provideDatabase(get()) }
    factory { CoinDatabase.provideDao(get()) }
}