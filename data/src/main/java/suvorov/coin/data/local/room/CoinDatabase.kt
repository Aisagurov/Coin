package suvorov.coin.data.local.room

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import suvorov.coin.data.local.room.model.*

@Database(
    entities = [
        GlobalEntity::class,
        CurrencyEntity::class,
        CategoryEntity::class,
        ExchangeEntity::class,
        DerivativeEntity::class
    ], version = 1, exportSchema = false)
abstract class CoinDatabase: RoomDatabase() {
    abstract fun coinDao(): CoinDao

    companion object {
        fun provideDatabase(application: Application): CoinDatabase =
            Room.databaseBuilder(application, CoinDatabase::class.java, "CoinDatabase")
                .fallbackToDestructiveMigration()
                .build()

        fun provideDao(database: CoinDatabase): CoinDao = database.coinDao()
    }
}