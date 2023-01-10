package suvorov.coin.di

import org.koin.dsl.module
import suvorov.coin.data.local.firebase.CoinFirebaseDatabase
import suvorov.coin.domain.repository.FirebaseDatabase

val firebaseModule = module {
    single<FirebaseDatabase> {
        CoinFirebaseDatabase()
    }
}