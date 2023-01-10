package suvorov.coin.data.repository.category

import suvorov.coin.data.local.room.CoinDao

class CategoryLocalDataSource(private val dao: CoinDao) {
    fun categoryById(id: String) = dao.categoryById(id)
}