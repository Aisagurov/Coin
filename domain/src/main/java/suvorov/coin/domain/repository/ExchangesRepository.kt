package suvorov.coin.domain.repository

import kotlinx.coroutines.flow.Flow
import suvorov.coin.domain.model.Exchange

interface ExchangesRepository {
    val exchangesListFromDatabase: Flow<List<Exchange>>
    suspend fun getExchangesFromApi(count: Int)
    fun loadData(): Boolean
}