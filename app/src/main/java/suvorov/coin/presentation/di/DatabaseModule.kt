package suvorov.coin.presentation.di

import android.app.Application
import androidx.room.Room
import org.koin.dsl.module
import suvorov.coin.data.local.database.CoinDao
import suvorov.coin.data.local.database.CoinDatabase

val databaseModule = module {
    single { provideDatabase(get()) }
    single { provideDao(get()) }
}

fun provideDatabase(application: Application): CoinDatabase {
    return Room.databaseBuilder(application, CoinDatabase::class.java, "CoinDatabase").build()
}

fun provideDao(database: CoinDatabase): CoinDao {
    return database.coinDao()
}