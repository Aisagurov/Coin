package suvorov.coin.data.repository.list

import suvorov.coin.data.remote.ApiService

class ListRemoteDataSource(private val service: ApiService) {
    suspend fun getCoinsList(coin: String) = service.getCoinsList(coin)
}