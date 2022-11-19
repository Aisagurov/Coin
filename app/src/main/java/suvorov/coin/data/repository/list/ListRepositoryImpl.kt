package suvorov.coin.data.repository.list

import suvorov.coin.data.local.database.CoinEntity
import suvorov.coin.data.local.preferences.PreferenceStorage
import suvorov.coin.domain.repository.ListRepository
import suvorov.coin.utils.emptyIfNull
import java.util.*

class ListRepositoryImpl(
    private val localDataSource: ListLocalDataSource,
    private val remoteDataSource: ListRemoteDataSource,
    private val preferenceStorage: PreferenceStorage
    ): ListRepository {

    override val coinsListFromDatabase = localDataSource.coinsList

    override val favoritesListFromDatabase = localDataSource.favoritesList

    override suspend fun getCoinsList(coin: String) {
        val favoritesSymbols = localDataSource.favoritesSymbols()
        val coinsList = remoteDataSource.getCoinsList(coin).let { list ->
            list.map {
                CoinEntity(
                    it.symbol.emptyIfNull(),
                    it.id,
                    it.name,
                    it.image,
                    it.price,
                    it.changePrice,
                    favoritesSymbols.contains(it.symbol)
                )
            }
        }
        localDataSource.insertCoinsIntoDatabase(coinsList)
        preferenceStorage.timeLoadedAt = Date().time
    }

    override suspend fun updateFavoritesStatus(symbol: String) =
        localDataSource.updateFavoritesStatus(symbol)

    override fun loadData(): Boolean {
        val lastLoadedTime = preferenceStorage.timeLoadedAt
        val currentTime = Date().time
        return currentTime - lastLoadedTime > 600 * 1000
    }
}