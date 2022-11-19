package suvorov.coin.presentation.di

import android.app.Application
import org.koin.dsl.module
import suvorov.coin.data.local.preferences.PreferenceStorage
import suvorov.coin.data.local.preferences.SharedPreferenceStorage

val preferenceModule = module {
    single { providePreferenceStorage(get()) }
}

fun providePreferenceStorage(application: Application): PreferenceStorage {
    return SharedPreferenceStorage(application)
}