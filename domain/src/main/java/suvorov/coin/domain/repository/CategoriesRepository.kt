package suvorov.coin.domain.repository

import kotlinx.coroutines.flow.Flow
import suvorov.coin.domain.model.Category

interface CategoriesRepository {
    val categoriesListFromDatabase: Flow<List<Category>>
    suspend fun getCategoriesFromApi()
    fun loadData(): Boolean
}