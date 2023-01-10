package suvorov.coin.data.repository.exchanges

import suvorov.coin.data.remote.ApiService

class ExchangesRemoteDataSource(private val service: ApiService) {
    suspend fun getExchanges(count: Int) = service.getExchanges(count)
}