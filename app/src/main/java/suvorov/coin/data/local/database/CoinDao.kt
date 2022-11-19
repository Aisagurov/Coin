package suvorov.coin.data.local.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CoinDao {
    @Query("SELECT * FROM coin")
    fun coinsList(): LiveData<List<CoinEntity>>

    @Query("SELECT * FROM coin WHERE symbol = :symbol")
    suspend fun coinFromSymbol(symbol: String): CoinEntity?

    @Query("SELECT * FROM coin WHERE symbol = :symbol")
    fun coinLiveDataFromSymbol(symbol: String): LiveData<CoinEntity>

    @Query("SELECT * FROM coin WHERE isFavorite = 1")
    fun favoritesList(): LiveData<List<CoinEntity>>

    @Query("SELECT symbol FROM coin WHERE isFavorite = 1")
    suspend fun favoritesSymbols(): List<String>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(list: List<CoinEntity>)

    @Update
    suspend fun update(coin: CoinEntity): Int
}