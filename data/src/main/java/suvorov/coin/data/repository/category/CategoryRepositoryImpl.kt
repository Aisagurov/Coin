package suvorov.coin.data.repository.category

import kotlinx.coroutines.flow.map
import suvorov.coin.data.mapper.CategoryEntityMapper
import suvorov.coin.domain.repository.CategoryRepository

class CategoryRepositoryImpl(
    private val localDataSource: CategoryLocalDataSource
): CategoryRepository {

    override fun categoryById(id: String) =
        localDataSource.categoryById(id).map { CategoryEntityMapper.map(it) }
}