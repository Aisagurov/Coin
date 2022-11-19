package suvorov.coin.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CoinEntity::class], version = 1, exportSchema = false)
abstract class CoinDatabase: RoomDatabase() {
    abstract fun coinDao(): CoinDao
}