package suvorov.coin.domain.interactor

import androidx.lifecycle.LiveData
import suvorov.coin.data.local.database.CoinEntity
import suvorov.coin.data.remote.HistoricalPrice

interface HistoryInteractor {

    fun coinFromSymbol(symbol: String): LiveData<CoinEntity>

    suspend fun getHistoricalPrice(id: String): HistoricalPrice
}