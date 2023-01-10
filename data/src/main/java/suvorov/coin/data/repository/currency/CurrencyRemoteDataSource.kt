package suvorov.coin.data.repository.currency

import suvorov.coin.data.remote.ApiService

class CurrencyRemoteDataSource(private val service: ApiService) {
    suspend fun getCurrencyHistory(id: String, currency: String, days: Int) =
        service.getCurrencyHistory(id, currency, days)
}