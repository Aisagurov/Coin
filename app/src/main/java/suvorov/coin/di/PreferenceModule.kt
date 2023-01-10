package suvorov.coin.di

import org.koin.dsl.module
import suvorov.coin.data.local.preferences.SharedPreferenceStorage
import suvorov.coin.domain.repository.PreferenceStorage

val preferenceModule = module {
    single<PreferenceStorage> {
        SharedPreferenceStorage(get())
    }
}