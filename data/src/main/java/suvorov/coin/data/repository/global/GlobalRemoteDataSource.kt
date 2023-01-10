package suvorov.coin.data.repository.global

import suvorov.coin.data.remote.ApiService

class GlobalRemoteDataSource(private val service: ApiService) {
    suspend fun getGlobal() = service.getGlobal()
}