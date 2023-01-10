package suvorov.coin.data.repository.currencies

import suvorov.coin.data.remote.ApiService

class CurrenciesRemoteDataSource(private val service: ApiService) {
    suspend fun getCurrencies(currency: String, count: Int) = service.getCurrencies(currency, count)
}