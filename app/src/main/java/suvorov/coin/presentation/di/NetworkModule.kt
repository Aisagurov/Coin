package suvorov.coin.presentation.di

import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.gson.*
import org.koin.dsl.module
import suvorov.coin.data.remote.ApiService

val networkModule = module {
    single { ApiService(get()) }
    single { provideHttpClient() }
}

fun provideHttpClient() = HttpClient(Android) {
    install(ContentNegotiation) {
        gson()
    }
    engine {
        connectTimeout = 10000
    }
}