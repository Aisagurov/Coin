package suvorov.coin

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import suvorov.coin.di.*

class App: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(
                networkModule,
                databaseModule,
                firebaseModule,
                preferenceModule,
                dataSourceModule,
                repositoryModule,
                viewModelModule
            )
        }
    }
}