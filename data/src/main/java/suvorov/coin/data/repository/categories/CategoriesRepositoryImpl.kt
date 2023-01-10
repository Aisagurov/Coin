package suvorov.coin.data.repository.categories

import kotlinx.coroutines.flow.map
import suvorov.coin.data.local.preferences.PreferencesConstants.LOAD_MILLISECONDS
import suvorov.coin.data.local.preferences.PreferencesConstants.LOAD_SECONDS
import suvorov.coin.data.mapper.CategoryApiMapper
import suvorov.coin.data.mapper.CategoryEntityMapper
import suvorov.coin.domain.repository.CategoriesRepository
import suvorov.coin.domain.repository.PreferenceStorage
import java.util.*

class CategoriesRepositoryImpl(
    private val remoteDataSource: CategoriesRemoteDataSource,
    private val localDataSource: CategoriesLocalDataSource,
    private val preferenceStorage: PreferenceStorage
): CategoriesRepository {

    override val categoriesListFromDatabase =
        localDataSource.categoriesList.map { list ->
            list.filter { it.id.isEmpty().not() }
                .map { CategoryEntityMapper.map(it) }
        }

    override suspend fun getCategoriesFromApi() {
        val customList = remoteDataSource.getCategories().let { list ->
            list.filter { it.id.isNullOrEmpty().not() }
                .map { CategoryApiMapper.map(it) }
        }
        localDataSource.insertCategoriesIntoDatabase(customList)
        preferenceStorage.timeLoadedAtCategory = Date().time
    }

    override fun loadData(): Boolean {
        val lastLoadedTime = preferenceStorage.timeLoadedAtCategory
        val currentTime = Date().time
        return currentTime - lastLoadedTime > LOAD_SECONDS * LOAD_MILLISECONDS
    }
}