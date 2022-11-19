package suvorov.coin.domain.repository

import androidx.lifecycle.LiveData
import suvorov.coin.data.remote.HistoricalPrice
import suvorov.coin.data.local.database.CoinEntity

interface HistoryRepository {

    fun coinFromSymbol(symbol: String): LiveData<CoinEntity>

    suspend fun getHistoricalPrice(id: String, coin: String = "usd"): HistoricalPrice
}