package suvorov.coin.data.repository.derivatives

import suvorov.coin.data.remote.ApiService

class DerivativesRemoteDataSource(private val service: ApiService) {
    suspend fun getDerivatives(count: Int) = service.getDerivatives(count)
}