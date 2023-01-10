package suvorov.coin.data.repository.categories

import suvorov.coin.data.remote.ApiService

class CategoriesRemoteDataSource(private val service: ApiService) {
    suspend fun getCategories() = service.getCategories()
}