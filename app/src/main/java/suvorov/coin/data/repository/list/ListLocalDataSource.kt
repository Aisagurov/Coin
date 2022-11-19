package suvorov.coin.data.repository.list

import suvorov.coin.data.local.database.CoinDao
import suvorov.coin.data.local.database.CoinEntity

class ListLocalDataSource(private val dao: CoinDao) {

    val coinsList = dao.coinsList()

    val favoritesList = dao.favoritesList()

    suspend fun insertCoinsIntoDatabase(list: List<CoinEntity>) {
        if (list.isNotEmpty()) {
            dao.insert(list)
        }
    }

    suspend fun favoritesSymbols(): List<String> = dao.favoritesSymbols()

    suspend fun updateFavoritesStatus(symbol: String): CoinEntity? {
        val coin = dao.coinFromSymbol(symbol)
        coin?.let {
            val coinEntity = CoinEntity(
                it.symbol,
                it.id,
                it.name,
                it.image,
                it.price,
                it.changePrice,
                it.isFavorite.not()
            )
            if (dao.update(coinEntity) > 0) {
                return coinEntity
            }
        }
        return null
    }
}