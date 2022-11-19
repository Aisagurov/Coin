package suvorov.coin.domain.repository

import androidx.lifecycle.LiveData
import suvorov.coin.data.local.database.CoinEntity

interface ListRepository {

    val coinsListFromDatabase: LiveData<List<CoinEntity>>

    val favoritesListFromDatabase: LiveData<List<CoinEntity>>

    suspend fun getCoinsList(coin: String)

    suspend fun updateFavoritesStatus(symbol: String): CoinEntity?

    fun loadData(): Boolean
}