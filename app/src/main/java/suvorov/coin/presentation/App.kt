package suvorov.coin.presentation

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin
import suvorov.coin.presentation.di.*

class App: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(
                networkModule,
                databaseModule,
                preferenceModule,
                dataSourceModule,
                repositoryModule,
                interactorModule,
                viewModelModule
            )
        }
    }
}