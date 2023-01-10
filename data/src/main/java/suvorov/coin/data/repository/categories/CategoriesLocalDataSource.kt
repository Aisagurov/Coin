package suvorov.coin.data.repository.categories

import suvorov.coin.data.local.room.CoinDao
import suvorov.coin.data.local.room.model.CategoryEntity

class CategoriesLocalDataSource(private val dao: CoinDao) {

    val categoriesList = dao.categoriesList()

    suspend fun insertCategoriesIntoDatabase(list: List<CategoryEntity>) {
        if (list.isNotEmpty()) {
            dao.insertCategories(list)
        }
    }
}