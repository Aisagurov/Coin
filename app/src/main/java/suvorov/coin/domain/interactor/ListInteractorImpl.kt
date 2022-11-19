package suvorov.coin.domain.interactor

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import suvorov.coin.domain.repository.ListRepository

class ListInteractorImpl(private val repository: ListRepository): ListInteractor {

    override val coinsListFromDatabase = repository.coinsListFromDatabase

    override val favoritesListFromDatabase = repository.favoritesListFromDatabase

    override suspend fun getCoinsList(coin: String) =
        withContext(Dispatchers.IO) {
            repository.getCoinsList(coin)
        }

    override suspend fun updateFavoritesStatus(symbol: String) =
        withContext(Dispatchers.IO) {
            repository.updateFavoritesStatus(symbol)
        }

    override fun loadData() = repository.loadData()
}