package suvorov.coin.data.local.room

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import suvorov.coin.data.local.room.model.*

@Dao
interface CoinDao {
    //global
    @Query("SELECT * FROM global")
    fun global(): Flow<GlobalEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGlobal(global: GlobalEntity)

    //currencies
    @Query("SELECT * FROM currency")
    fun currenciesList(): Flow<List<CurrencyEntity>>

    @Query("SELECT * FROM currency WHERE id = :id")
    suspend fun getCurrencyById(id: String): CurrencyEntity?

    @Query("SELECT * FROM currency WHERE id = :id")
    fun currencyById(id: String): Flow<CurrencyEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrencies(list: List<CurrencyEntity>)

    @Update
    suspend fun updateCurrencies(coin: CurrencyEntity): Int

    //categories
    @Query("SELECT * FROM category")
    fun categoriesList(): Flow<List<CategoryEntity>>

    @Query("SELECT * FROM category WHERE id = :id")
    fun categoryById(id: String): Flow<CategoryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategories(list: List<CategoryEntity>)

    //exchanges
    @Query("SELECT * FROM exchange")
    fun exchangesList(): Flow<List<ExchangeEntity>>

    @Query("SELECT * FROM exchange WHERE id = :id")
    fun exchangeById(id: String): Flow<ExchangeEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExchanges(list: List<ExchangeEntity>)

    //exchanges
    @Query("SELECT * FROM derivative")
    fun derivativesList(): Flow<List<DerivativeEntity>>

    @Query("SELECT * FROM derivative WHERE name = :name")
    fun derivativeByName(name: String): Flow<DerivativeEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDerivatives(list: List<DerivativeEntity>)

    //portfolio
    @Query("SELECT * FROM currency WHERE isPortfolio = 1")
    fun portfolioList(): Flow<List<CurrencyEntity>>

    @Query("SELECT id FROM currency WHERE isPortfolio = 1")
    suspend fun portfolioIds(): List<String>
}