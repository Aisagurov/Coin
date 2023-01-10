package suvorov.coin.domain.repository

import kotlinx.coroutines.flow.Flow
import suvorov.coin.domain.model.Category

interface CategoryRepository {
    fun categoryById(id: String): Flow<Category>
}