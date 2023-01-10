package suvorov.coin.domain.repository

import kotlinx.coroutines.flow.Flow
import suvorov.coin.domain.model.Currency

interface CurrenciesRepository {
    val currenciesListFromDatabase: Flow<List<Currency>>
    suspend fun getCurrenciesFromApi(currency: String = "usd", count: Int = 250)
    suspend fun updatePortfolioStatus(id: String): Currency?
    fun loadData(): Boolean
}