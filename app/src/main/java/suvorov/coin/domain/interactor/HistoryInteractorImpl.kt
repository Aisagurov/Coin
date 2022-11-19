package suvorov.coin.domain.interactor

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import suvorov.coin.domain.repository.HistoryRepository

class HistoryInteractorImpl(private val repository: HistoryRepository): HistoryInteractor {

    override fun coinFromSymbol(symbol: String) = repository.coinFromSymbol(symbol)

    override suspend fun getHistoricalPrice(id: String) =
        withContext(Dispatchers.IO) {
            repository.getHistoricalPrice(id)
    }
}