package suvorov.coin.di

import org.koin.dsl.module
import suvorov.coin.data.remote.ApiService

val networkModule = module {
    single { ApiService() }
}