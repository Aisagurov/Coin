package suvorov.coin.data.repository.exchange

import suvorov.coin.data.remote.ApiService

class ExchangeRemoteDataSource(private val service: ApiService) {
    suspend fun getExchangeHistory(id: String, days: Int) = service.getExchangeHistory(id, days)
}