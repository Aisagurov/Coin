package suvorov.coin.domain.repository

import kotlinx.coroutines.flow.Flow
import suvorov.coin.domain.model.Currency

interface PortfolioRepository {
    val portfolioCurrencies: Flow<List<Currency>>
    suspend fun updatePortfolioStatus(id: String): Currency?
}