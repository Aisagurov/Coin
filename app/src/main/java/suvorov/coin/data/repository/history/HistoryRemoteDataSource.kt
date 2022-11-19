package suvorov.coin.data.repository.history

import suvorov.coin.data.remote.ApiService

class HistoryRemoteDataSource(private val service: ApiService) {
    suspend fun getHistoricalPrice(id: String, coin: String, days: Int = 30) =
        service.getHistoricalPrice(id, coin, days)
}