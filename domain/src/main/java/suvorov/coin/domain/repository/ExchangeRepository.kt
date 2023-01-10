package suvorov.coin.domain.repository

import kotlinx.coroutines.flow.Flow
import suvorov.coin.domain.model.Exchange

interface ExchangeRepository {
    fun exchangeById(id: String): Flow<Exchange>
    suspend fun getExchangeHistory(id: String, days: Int = 7): List<DoubleArray>
}