package suvorov.coin.domain.repository

import kotlinx.coroutines.flow.Flow
import suvorov.coin.domain.model.Currency

interface CurrencyRepository {
    fun currencyById(id: String): Flow<Currency>
    suspend fun getCurrencyHistory(id: String, currency: String = "usd", days: Int = 7): List<DoubleArray>
    suspend fun updatePortfolioStatus(id: String): Currency?
}